package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.TransactionDTO;
import com.microservices.accountservice.enums.MemberRanking;
import com.microservices.accountservice.exception.LimitExceededException;
import com.microservices.accountservice.model.Account;
import com.microservices.accountservice.model.Limits;
import com.microservices.accountservice.model.Transaction;
import com.microservices.accountservice.repository.AccountRepository;
import com.microservices.accountservice.repository.LimitsRepository;
import com.microservices.accountservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Service for processing transactions on accounts.
 * This service handles the validation of transaction limits, updating account balances, and updating account rankings.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final AccountRepository accountRepository;
    private final LimitsRepository limitsRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Processes a transaction for a given account.
     * Validates the transaction limits and updates the account balance and ranking.
     *
     * @param transactionDTO The transaction data transfer object containing transaction details.
     * @throws LimitExceededException If the transaction exceeds any limits.
     */
    @Override
    public void processTransaction(TransactionDTO transactionDTO) throws LimitExceededException {
        // Fetch the account by account number
        Account account = accountRepository.findByAccountNumber(transactionDTO.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Fetch the limits for the account
        Limits limits = limitsRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new IllegalArgumentException("Limits not found for account"));

        BigDecimal transactionAmount = transactionDTO.getAmount();
        LocalDateTime now = LocalDateTime.now();

        // Validate the transaction limits
        validateTransactionLimits(account, transactionAmount, limits, now);

        // Update the account balance
        account.setBalance(account.getBalance().subtract(transactionAmount));

        // Update the account ranking
        updateAccountRanking(account);

        // Save the updated account
        accountRepository.save(account);

        // Log the transaction
        logTransaction(account, transactionAmount, now);
    }

    /**
     * Updates the ranking of an account based on its balance.
     *
     * @param account The account to be updated.
     */
    private void updateAccountRanking(Account account) {
        BigDecimal balance = account.getBalance();

        if (balance.compareTo(new BigDecimal("5000")) <= 0) {
            account.setMemberRanking(MemberRanking.STANDARD);
        } else if (balance.compareTo(new BigDecimal("5000")) > 0 && balance.compareTo(new BigDecimal("100000")) <= 0) {
            account.setMemberRanking(MemberRanking.METAL);
        } else if (balance.compareTo(new BigDecimal("100000")) > 0 && balance.compareTo(new BigDecimal("200000")) <= 0) {
            account.setMemberRanking(MemberRanking.PRO);
        } else if (balance.compareTo(new BigDecimal("200000")) > 0) {
            account.setMemberRanking(MemberRanking.ULTIMATE);
        }
    }

    /**
     * Logs a transaction in the transaction repository.
     *
     * @param account          The account for which the transaction is made.
     * @param transactionAmount The amount of the transaction.
     * @param now              The timestamp of the transaction.
     */
    private void logTransaction(Account account, BigDecimal transactionAmount, LocalDateTime now) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(transactionAmount);
        transaction.setTransactionDate(now);
        transaction.setAccount(account);
        transactionRepository.save(transaction);
    }

    /**
     * Validates the transaction limits for a given account.
     *
     * @param account           The account to be validated.
     * @param transactionAmount The amount of the transaction.
     * @param limits            The limits of the account.
     * @param now               The current date and time.
     * @throws LimitExceededException If the transaction exceeds any limits.
     */
    @Override
    public void validateTransactionLimits(Account account, BigDecimal transactionAmount, Limits limits, LocalDateTime now) throws LimitExceededException {
        if (transactionAmount.compareTo(limits.getTransactionLimit()) > 0) {
            throw new LimitExceededException("Transaction limit exceeded");
        }

        // Implement daily, weekly, and monthly limit checks
        BigDecimal dailyTotal = transactionRepository.findByAccountAndTransactionDateBetween(account, now.toLocalDate().atStartOfDay(), now)
                .stream()
                .map(Transaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dailyTotal.add(transactionAmount).compareTo(limits.getDailyLimit()) > 0) {
            throw new LimitExceededException("Daily limit exceeded");
        }

        // Similar checks for weekly and monthly limits can be added here
        BigDecimal weeklyTotal = transactionRepository.findByAccountAndTransactionDateBetween(account, now.minusDays(7), now)
                .stream()
                .map(Transaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (weeklyTotal.add(transactionAmount).compareTo(limits.getWeeklyLimit()) > 0) {
            throw new LimitExceededException("Weekly limit exceeded");
        }

        BigDecimal monthlyTotal = transactionRepository.findByAccountAndTransactionDateBetween(account, now.minusDays(30), now)
                .stream()
                .map(Transaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (monthlyTotal.add(transactionAmount).compareTo(limits.getMonthlyLimit()) > 0) {
            throw new LimitExceededException("Monthly limit exceeded");
        }
    }
}
