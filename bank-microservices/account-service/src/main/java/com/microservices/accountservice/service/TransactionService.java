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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final AccountRepository accountRepository;
    private final LimitsRepository limitsRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Adds money to the specified account.
     *
     * @param transactionDTO the transaction details.
     * @throws LimitExceededException if the transaction exceeds the limits.
     */
    @Override
    @Transactional
    public void addMoney(TransactionDTO transactionDTO) throws LimitExceededException {
        // Fetch the account by account number
        Account account = accountRepository.findByAccountNumber(transactionDTO.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Fetch the limits for the account
        Limits limits = limitsRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new IllegalArgumentException("Limits not found for account"));

        BigDecimal transactionAmount = transactionDTO.getAmount();
        LocalDateTime now = LocalDateTime.now();

        // Validate the transaction limits for deposit
        validateTransactionLimits(account, transactionAmount, limits, now, true);

        // Update the account balance
        account.setBalance(account.getBalance().add(transactionAmount));

        // Update the account ranking
        updateAccountRanking(account);

        // Save the updated account
        accountRepository.save(account);

        // Log the transaction
        logTransaction(account, transactionAmount, now, true);
    }

    /**
     * Withdraws money from the specified account.
     * @param transactionDTO the transaction details.
     * @throws LimitExceededException
     */
    @Override
    @Transactional
    public void withdrawMoney(TransactionDTO transactionDTO) throws LimitExceededException {
        // Fetch the account by account number
        Account account = accountRepository.findByAccountNumber(transactionDTO.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Fetch the limits for the account
        Limits limits = limitsRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new IllegalArgumentException("Limits not found for account"));

        BigDecimal transactionAmount = transactionDTO.getAmount();
        LocalDateTime now = LocalDateTime.now();

        // Validate the transaction limits for withdrawal
        validateTransactionLimits(account, transactionAmount, limits, now, false);

        // Check if the account has sufficient balance for the withdrawal
        if (account.getBalance().compareTo(transactionAmount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Update the account balance
        account.setBalance(account.getBalance().subtract(transactionAmount));

        // Update the account ranking
        updateAccountRanking(account);

        // Save the updated account
        accountRepository.save(account);

        // Log the transaction
        logTransaction(account, transactionAmount, now, false);
    }

    /**
     * Processes a transaction for the specified account.
     * @param transactionDTO the transaction details.
     * @throws LimitExceededException
     */
    @Override
    @Transactional
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
        validateTransactionLimits(account, transactionAmount, limits, now, false);

        // Update the account balance
        account.setBalance(account.getBalance().subtract(transactionAmount));

        // Update the account ranking
        updateAccountRanking(account);

        // Save the updated account
        accountRepository.save(account);

        // Log the transaction
        logTransaction(account, transactionAmount, now, false);
    }

    /**
     * Update the account ranking based on the account balance.
     * @param account the account to update.
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
     * Log the transaction to the database.
     * @param account the account to log the transaction for.
     * @param transactionAmount the amount of the transaction.
     * @param now the current date and time.
     * @param isDeposit true if the transaction is a deposit, false if it is a withdrawal.
     */
    private void logTransaction(Account account, BigDecimal transactionAmount, LocalDateTime now, boolean isDeposit) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(isDeposit ? transactionAmount : transactionAmount.negate());
        transaction.setTransactionDate(now);
        transaction.setAccount(account);
        transactionRepository.save(transaction);
    }

    /**
     * Validate the transaction limits for a given account.
     * @param account the account to be validated.
     * @param transactionAmount the amount of the transaction.
     * @param limits the limits of the account.
     * @param now the current date and time.
     * @param isDeposit true if the transaction is a deposit, false if it is a withdrawal.
     * @throws LimitExceededException if the transaction exceeds any limits.
     */
    @Override
    public void validateTransactionLimits(Account account, BigDecimal transactionAmount, Limits limits, LocalDateTime now, boolean isDeposit) throws LimitExceededException {
        if (transactionAmount.compareTo(limits.getTransactionLimit()) > 0) {
            throw new LimitExceededException("Transaction limit exceeded");
        }

        BigDecimal dailyLimit = isDeposit ? limits.getDailyDepositLimit() : limits.getDailyWithdrawLimit();
        BigDecimal weeklyLimit = isDeposit ? limits.getWeeklyDepositLimit() : limits.getWeeklyWithdrawLimit();
        BigDecimal monthlyLimit = isDeposit ? limits.getMonthlyDepositLimit() : limits.getMonthlyWithdrawLimit();

        BigDecimal dailyTotal = transactionRepository.findByAccountAndTransactionDateBetween(account, now.toLocalDate().atStartOfDay(), now)
                .stream()
                .filter(transaction -> (isDeposit && transaction.getTransactionAmount().compareTo(BigDecimal.ZERO) > 0) ||
                        (!isDeposit && transaction.getTransactionAmount().compareTo(BigDecimal.ZERO) < 0))
                .map(transaction -> transaction.getTransactionAmount().abs())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dailyTotal.add(transactionAmount).compareTo(dailyLimit) > 0) {
            throw new LimitExceededException("Daily limit exceeded");
        }

        BigDecimal weeklyTotal = transactionRepository.findByAccountAndTransactionDateBetween(account, now.minusDays(7), now)
                .stream()
                .filter(transaction -> (isDeposit && transaction.getTransactionAmount().compareTo(BigDecimal.ZERO) > 0) ||
                        (!isDeposit && transaction.getTransactionAmount().compareTo(BigDecimal.ZERO) < 0))
                .map(transaction -> transaction.getTransactionAmount().abs())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (weeklyTotal.add(transactionAmount).compareTo(weeklyLimit) > 0) {
            throw new LimitExceededException("Weekly limit exceeded");
        }

        BigDecimal monthlyTotal = transactionRepository.findByAccountAndTransactionDateBetween(account, now.minusDays(30), now)
                .stream()
                .filter(transaction -> (isDeposit && transaction.getTransactionAmount().compareTo(BigDecimal.ZERO) > 0) ||
                        (!isDeposit && transaction.getTransactionAmount().compareTo(BigDecimal.ZERO) < 0))
                .map(transaction -> transaction.getTransactionAmount().abs())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (monthlyTotal.add(transactionAmount).compareTo(monthlyLimit) > 0) {
            throw new LimitExceededException("Monthly limit exceeded");
        }
    }
}
