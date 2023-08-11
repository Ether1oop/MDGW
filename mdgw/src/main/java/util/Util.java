package util;

import dataType.PosAmt;
import message.*;
import netty_message.MsgType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class Util {
    private static Map<MsgType, Class<? extends Message>> pakMap;

    static {
        pakMap = new HashMap<>();
        pakMap.put(MsgType.LOGON, Logon.class);
        pakMap.put(MsgType.LOGOUT, Logout.class);
        pakMap.put(MsgType.HEARTBEAT, Heartbeat.class);
        pakMap.put(MsgType.CHANNEL_HEARTBEAT, ChannelHeartbeat.class);
        pakMap.put(MsgType.SPOT_CENTRALIZED_AUCTION_TRADING_SNAPSHOT_MARKET, Market_300111.class);
        pakMap.put(MsgType.BOND_SNAPSHOT_MARKET, Market_300211.class);
        pakMap.put(MsgType.AFTER_HOURS_PRICING_BLOCK_TRADE_SNAPSHOT_MARKET, Market_300611.class);
        pakMap.put(MsgType.AFTER_HOURS_PRICING_TRADE_SNAPSHOT_MARKET, Market_303711.class);
        pakMap.put(MsgType.HK_LIVE_MARKET, Market_306311.class);
        pakMap.put(MsgType.INDEX_SNAPSHOT_MARKET, Market_309011.class);
        pakMap.put(MsgType.VOLUME_STATISTICS_INDICATOR_SNAPSHOT_MARKET, Market_309111.class);
        pakMap.put(MsgType.SPOT_CENTRALIZED_AUCTION_TRANSACTIONS_MARKET, OrderMarket_300192.class);
        pakMap.put(MsgType.AGREEMENT_TRANSACTIONS_TICK_INTENT_QUOTES, OrderMarket_300592.class);
        pakMap.put(MsgType.TICK_QUOTES_FOR_REFINANCING_SECURITIES_LENDING, OrderMarket_300792.class);
        pakMap.put(MsgType.BOND_SPOT_TRADING_MATCHING_TRANSACTIONS_TICK_QUOTE, OrderMarket_300292.class);
        pakMap.put(MsgType.BOND_SPOT_TRADING_CLICK_TRANSACTION_TICK_QUOTE, OrderMarket_300392.class);
        pakMap.put(MsgType.BOND_SPOT_TRADING_BIDDING_AND_TRANSACTION_QUOTE, OrderMarket_300492.class);
        pakMap.put(MsgType.SPOT_CENTRALIZED_AUCTION_TRANSACTION_TICK_MARKET, OrderDeal_300191.class);
        pakMap.put(MsgType.AGREEMENT_TRANSACTION_INTENTION_QUOTATION, OrderDeal_300591.class);
        pakMap.put(MsgType.TICK_QUOTES_FOR_REFINANCING_SECURITIES_LENDING_DEAL, OrderDeal_300791.class);
        pakMap.put(MsgType.BOND_SPOT_TRADING_MATCHING_TRANSACTIONS_TICK_QUOTE_DEAL, OrderDeal_300391.class);
        pakMap.put(MsgType.SPOT_BOND_TRADING_BIDDING_AND_TRANSACTIONS_QUOTES, OrderDeal_300491.class);
    }

    public static Optional<Class<? extends Message>> getMessage(MsgType msgType){
        if(msgType == null){
            return Optional.empty();
        }
        return Optional.ofNullable(pakMap.get(msgType));
    }

    public static uint32 GenerateCheckSum(byte[] buff){
        long cks = 0;
        int idx = 0;
        // 这里减去4是因为我们多申请了消息尾的空间，但是这里只需要计算消息头+消息体的内容
        while(idx < buff.length - 4){
            cks += buff[idx++];
        }
        return new uint32(cks % 256);
    }

}
