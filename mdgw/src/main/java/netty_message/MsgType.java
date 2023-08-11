package netty_message;

import java.util.*;

public enum MsgType {
    // 登陆消息
    LOGON(1),
    // 注销消息
    LOGOUT(2),
    // 心跳消息
    HEARTBEAT(3),
    // 频道心跳
    CHANNEL_HEARTBEAT(390095),
    // 重传消息
    RETRANSMISSION(390094),
    // 用户信息报告消息
    USER_REPORT(390093),
    // 快照行情频道统计
    SNAPSHOT_CHANNEL_STATISTICS(390090),
    // 业务拒绝消息
    BUSINESS_REJECT(8),
    // 市场实时状态
    MARKET_STATUS_REALTIME(390019),
    // 证券实时状态
    SECURITIES_STATUS_REALTIME(390013),
    // 公告
    ANNOUNCEMENT(390012),
    // 现货（股票，基金等）集中竞价交易快照行情 期权集中竞价交易快照行情
    SPOT_CENTRALIZED_AUCTION_TRADING_SNAPSHOT_MARKET(300111),
    // 债券通用质押式回购匹配交易快照行情  债券分销快照行情 债券现券交易快照行情
    BOND_SNAPSHOT_MARKET(300211),
    // 以收盘价交易的盘后定价大宗交易快照行情 以成交量加权平均价交易的盘后定价大宗交易快照行情
    AFTER_HOURS_PRICING_BLOCK_TRADE_SNAPSHOT_MARKET(300611),
    // 盘后定价交易快照行情
    AFTER_HOURS_PRICING_TRADE_SNAPSHOT_MARKET(303711),
    // 港股实时行情
    HK_LIVE_MARKET(306311),
    // 指数快照行情 国证指数快照行情
    INDEX_SNAPSHOT_MARKET(309011),
    // 成交量统计指标快照行情
    VOLUME_STATISTICS_INDICATOR_SNAPSHOT_MARKET(309111),
    // 现货（股票，基金，债券等）集中竞价交易逐笔行情
    SPOT_CENTRALIZED_AUCTION_TRANSACTIONS_MARKET(300192),
    // 协议交易逐笔意向行情
    AGREEMENT_TRANSACTIONS_TICK_INTENT_QUOTES(300592),
    // 转融通证券出借逐笔行情
    TICK_QUOTES_FOR_REFINANCING_SECURITIES_LENDING(300792),
    // 债券现券交易匹配成交逐笔行情
    BOND_SPOT_TRADING_MATCHING_TRANSACTIONS_TICK_QUOTE(300292),
    // 债券现券交易点击成交逐笔行情
    BOND_SPOT_TRADING_CLICK_TRANSACTION_TICK_QUOTE(300392),
    // 债券现券交易竞买成交逐笔行情
    BOND_SPOT_TRADING_BIDDING_AND_TRANSACTION_QUOTE(300492),
    // 现货（股票，基金，债券等）集中竞价交易逐笔行情
    SPOT_CENTRALIZED_AUCTION_TRANSACTION_TICK_MARKET(300191),
    // 协议交易逐笔意向行情
    AGREEMENT_TRANSACTION_INTENTION_QUOTATION(300591),
    // 转融通证券出借逐笔行情
    TICK_QUOTES_FOR_REFINANCING_SECURITIES_LENDING_DEAL(300791),
    // 债券现券交易匹配成交逐笔行情
    BOND_SPOT_TRADING_MATCHING_TRANSACTIONS_TICK_QUOTE_DEAL(300391),
    // 债券现券交易竞买成交逐笔行情
    SPOT_BOND_TRADING_BIDDING_AND_TRANSACTIONS_QUOTES(300491)
    ;

    private int code;

    MsgType(int code) {
        this.code = code;
    }

    private static Map<Integer, MsgType> map;

    static {
        map = new HashMap<>();
        Arrays.stream(MsgType.values()).forEach(msgType -> map.put(msgType.getCode(), msgType));
    }

    public static Optional<MsgType> lookup(int msgType){
        return Optional.ofNullable(map.get(msgType));
    }

    public int getCode() {
        return code;
    }
}
