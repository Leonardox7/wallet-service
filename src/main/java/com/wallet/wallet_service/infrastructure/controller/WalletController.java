package com.wallet.wallet_service.infrastructure.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.wallet_service.application.dto.CreateUserInput;
import com.wallet.wallet_service.application.dto.CreateUserOutput;
import com.wallet.wallet_service.application.dto.DepositFundsInput;
import com.wallet.wallet_service.application.dto.RetrieveBalanceInput;
import com.wallet.wallet_service.application.dto.RetrieveBalanceOutput;
import com.wallet.wallet_service.application.dto.RetrieveHistoricalBalanceInput;
import com.wallet.wallet_service.application.dto.RetrieveHistoricalBalanceOutput;
import com.wallet.wallet_service.application.dto.TransferFundsInput;
import com.wallet.wallet_service.application.dto.WithDrawFundsInput;
import com.wallet.wallet_service.application.usecase.CreateUser;
import com.wallet.wallet_service.application.usecase.DepositFunds;
import com.wallet.wallet_service.application.usecase.RetrieveBalance;
import com.wallet.wallet_service.application.usecase.RetrieveHistoricalBalance;
import com.wallet.wallet_service.application.usecase.TransferFunds;
import com.wallet.wallet_service.application.usecase.WithdrawFunds;
import com.wallet.wallet_service.infrastructure.constant.AppConstant;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "Wallet Service Assignment")
@Slf4j
public class WalletController {

    private final CreateUser createUser;
    private final DepositFunds depositFunds;
    private final RetrieveBalance retrieveBalance;
    private final RetrieveHistoricalBalance retrieveHistoricalBalance;
    private final TransferFunds transferFunds;
    private final WithdrawFunds withdrawFunds;

    public WalletController(
            CreateUser createUser,
            DepositFunds depositFunds,
            RetrieveBalance retrieveBalance,
            RetrieveHistoricalBalance retrieveHistoricalBalance,
            TransferFunds transferFunds,
            WithdrawFunds withdrawFunds) {
        this.createUser = createUser;
        this.depositFunds = depositFunds;
        this.retrieveBalance = retrieveBalance;
        this.retrieveHistoricalBalance = retrieveHistoricalBalance;
        this.transferFunds = transferFunds;
        this.withdrawFunds = withdrawFunds;
    }

    @PostMapping("/users")
    @ResponseBody
    CreateUserOutput createUser(@RequestBody CreateUserInput input) {
        log.info("Create user request: document: {} - name: {}", input.document(), input.name());
        return createUser.execute(input);
    }

    @GetMapping("/users/{id}/balances")
    @ResponseBody
    RetrieveBalanceOutput retrieveBalance(@PathVariable("id") String userId) {
        return retrieveBalance.execute(new RetrieveBalanceInput(userId));
    }

    @PostMapping("/deposits")
    @ResponseBody
    void depositFunds(@RequestBody DepositFundsInput input) {
        log.info("Deposit funds request: userId: {} - amount: {}", input.userId(), input.amount());
        depositFunds.execute(input);
    }

    @GetMapping("/users/{id}/historical-balances")
    @ResponseBody
    List<RetrieveHistoricalBalanceOutput> retrieveHistoricalBalance(@PathVariable("id") String userId,
            @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate) {

        LocalDateTime startTime = LocalDateTime.parse(startDate, AppConstant.formatteryyyyMMdd);
        LocalDateTime endTime = LocalDateTime.parse(endDate, AppConstant.formatteryyyyMMdd);

        return retrieveHistoricalBalance
                .execute(new RetrieveHistoricalBalanceInput(userId, startTime, endTime));
    }

    @PostMapping("/transfers")
    @ResponseBody
    void transferFunds(@RequestBody TransferFundsInput input) {
        log.info("Transfer funds request: fromUserId: {} - toUserId: {} - amount: {}", input.fromUserId(),
                input.toUserId(), input.amount());
        transferFunds.execute(input);
    }

    @PostMapping("/withdrawals")
    @ResponseBody
    void withdrawFunds(@RequestBody WithDrawFundsInput input) {
        log.info("Withdraw funds request: userId: {} - amount: {}", input.userId(), input.amount());
        withdrawFunds.execute(input);
    }
}
