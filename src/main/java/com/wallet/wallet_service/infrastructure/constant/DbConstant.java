package com.wallet.wallet_service.infrastructure.constant;

public class DbConstant {

    private DbConstant() {
    }

    public static final String FIND_WALLETS_BY_USER_ID = "SELECT w FROM Wallet w WHERE w.user.id = :userId";
    public static final String FIND_USER_BY_DOCUMENT = "SELECT u FROM User u WHERE u.document = :document";
    public static final String FIND_HISTORICAL_BALANCE_BY_USER_ID_AND_DATE = """
                SELECT h FROM HistoricalBalance h WHERE
                h.wallet.user.id = :userId AND
                date>=:startDate AND date<=:endDate
            """;
}
