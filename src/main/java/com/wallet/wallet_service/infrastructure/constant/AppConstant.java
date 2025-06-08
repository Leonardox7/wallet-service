package com.wallet.wallet_service.infrastructure.constant;

import java.time.format.DateTimeFormatter;

public class AppConstant {

    private AppConstant() {
    }

    public static final DateTimeFormatter formatteryyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
