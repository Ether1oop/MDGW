package message;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
@Getter
public class parse {

    public static void parseMessage(long msgType , ByteBuf message_body) throws IOException {
        ObjectOutputStream outputStream;

        outputStream = new ObjectOutputStream(new FileOutputStream("data.ser"));


        switch ((int) msgType){
            // 登陆消息
            case 1:
                Logon logon = new Logon();
                logon.read(message_body);
                logon.toStrings();
                outputStream.writeObject(logon);
                break;
            // 注销消息
            case 2:
                Logout logout = new Logout();
                logout.read(message_body);
                logout.toStrings();
                outputStream.writeObject(logout);
                break;
            // 心跳消息
            case 3:
                Heartbeat heartbeat = new Heartbeat();
                heartbeat.read(message_body);
                heartbeat.toStrings();
                outputStream.writeObject(heartbeat);
                break;
            // 频道心跳
            case 390095:
                ChannelHeartbeat channelHeartbeat = new ChannelHeartbeat();
                channelHeartbeat.read(message_body);
                channelHeartbeat.toStrings();
                outputStream.writeObject(channelHeartbeat);
                break;
            // 重传消息
            case 390094:
                ReTransmission reTransmission = new ReTransmission();
                reTransmission.read(message_body);
                reTransmission.toStrings();
                outputStream.writeObject(reTransmission);
                break;
            // 用户信息报告消息
            case 390093:
                UserInfoReport userInfoReport = new UserInfoReport();
                userInfoReport.read(message_body);
                userInfoReport.toStrings();
//                outputStream.writeObject(userInfoReport);
                break;
            // 快照行情频道统计
            case 390090:
                ChannelSnapShot channelSnapShot = new ChannelSnapShot();
                channelSnapShot.read(message_body);
                channelSnapShot.toStrings();
//                outputStream.writeObject(channelSnapShot);
                break;
            // 业务拒绝消息
            case 8:
                Reject reject = new Reject();
                reject.read(message_body);
                reject.toStrings();
//                outputStream.writeObject(reject);
                break;

            // 市场实时状态
            case 390019:
                MarketLiveStatus marketLiveStatus = new MarketLiveStatus();
                marketLiveStatus.read(message_body);
                marketLiveStatus.toStrings();
//                outputStream.writeObject(marketLiveStatus);
                break;
            // 证券实时状态
            case 390013:
                SecurityLiveStatus securityLiveStatus = new SecurityLiveStatus();
                securityLiveStatus.read(message_body);
                securityLiveStatus.toStrings();
//                outputStream.writeObject(securityLiveStatus);
                break;
            // 公告
            case 390012:
                Announcement announcement = new Announcement();
                announcement.read(message_body);
                announcement.toStrings();
//                outputStream.writeObject(announcement);
                break;
            // 现货（股票，基金等）集中竞价交易快照行情 期权集中竞价交易快照行情
            case 300111:
                Market_300111 market_300111 = new Market_300111();
                market_300111.read(message_body);
                market_300111.toStrings();
//                outputStream.writeObject(market_300111);
                break;
            // 债券通用质押式回购匹配交易快照行情  债券分销快照行情 债券现券交易快照行情
            case 300211:
                Market_300211 market_300211 = new Market_300211();
                market_300211.read(message_body);
                market_300211.toStrings();
//                outputStream.writeObject(market_300211);
                break;
            // 以收盘价交易的盘后定价大宗交易快照行情 以成交量加权平均价交易的盘后定价大宗交易快照行情
            case 300611:
                Market_300611 market_300611 = new Market_300611();
                market_300611.read(message_body);
                market_300611.toString();
//                outputStream.writeObject(market_300611);
                break;
            // 盘后定价交易快照行情
            case 303711:
                Market_303711 market_303711 = new Market_303711();
                market_303711.read(message_body);
                market_303711.toStrings();
//                outputStream.writeObject(market_303711);
                break;
            // 港股实时行情
            case 306311:
                Market_306311 market_306311 = new Market_306311();
                market_306311.read(message_body);
                market_306311.toStrings();
//                outputStream.writeObject(market_306311);
                break;
            // 指数快照行情 国证指数快照行情
            case 309011:
                Market_309011 market_309011 = new Market_309011();
                market_309011.read(message_body);
                market_309011.toStrings();
//                outputStream.writeObject(market_309011);
                break;
            // 成交量统计指标快照行情
            case 309111:
                Market_309111 market_309111 = new Market_309111();
                market_309111.read(message_body);
                market_309111.toStrings();
                outputStream.writeObject(market_309111);
                break;

            // 现货（股票，基金，债券等）集中竞价交易逐笔行情
            case 300192:
                OrderMarket_300192 orderMarket_300192 = new OrderMarket_300192();
                orderMarket_300192.read(message_body);
                orderMarket_300192.toStrings();
//                outputStream.writeObject(orderMarket_300192);
                break;
            // 协议交易逐笔意向行情
            case 300592:
                OrderMarket_300592 orderMarket_300592 = new OrderMarket_300592();
                orderMarket_300592.read(message_body);
                orderMarket_300592.toStrings();
//                outputStream.writeObject(orderMarket_300592);
                break;
            // 转融通证券出借逐笔行情
            case 300792:
                OrderMarket_300792 orderMarket_300792 = new OrderMarket_300792();
                orderMarket_300792.read(message_body);
                orderMarket_300792.toStrings();
//                outputStream.writeObject(orderMarket_300792);
                break;
            // 债券现券交易匹配成交逐笔行情
            case 300292:
                OrderMarket_300292 orderMarket_300292 = new OrderMarket_300292();
                orderMarket_300292.read(message_body);
                orderMarket_300292.toStrings();
//                outputStream.writeObject(orderMarket_300292);
                break;
            // 债券现券交易点击成交逐笔行情
            case 300392:
                OrderMarket_300392 orderMarket_300392 = new OrderMarket_300392();
                orderMarket_300392.read(message_body);
                orderMarket_300392.toStrings();
//                outputStream.writeObject(orderMarket_300392);
                break;
            // 债券现券交易竞买成交逐笔行情
            case 300492:
                OrderMarket_300492 orderMarket_300492 = new OrderMarket_300492();
                orderMarket_300492.read(message_body);
                orderMarket_300492.toStrings();
//                outputStream.writeObject(orderMarket_300492);
                break;


            // 现货（股票，基金，债券等）集中竞价交易逐笔行情
            case 300191:
                OrderDeal_300191 orderMarket_300191 = new OrderDeal_300191();
                orderMarket_300191.read(message_body);
                orderMarket_300191.toStrings();
//                outputStream.writeObject(orderMarket_300191);
                break;
            // 协议交易逐笔意向行情
            case 300591:
                OrderDeal_300591 orderMarket_300591 = new OrderDeal_300591();
                orderMarket_300591.read(message_body);
                orderMarket_300591.toStrings();
//                outputStream.writeObject(orderMarket_300591);
                break;
            // 转融通证券出借逐笔行情
            case 300791:
                OrderDeal_300791 orderMarket_300791 = new OrderDeal_300791();
                orderMarket_300791.read(message_body);
                orderMarket_300791.toStrings();
//                outputStream.writeObject(orderMarket_300791);
                break;
            // 债券现券交易匹配成交逐笔行情
            case 300391:
                OrderDeal_300391 orderDeal_300391 = new OrderDeal_300391();
                orderDeal_300391.read(message_body);
                orderDeal_300391.toStrings();
//                outputStream.writeObject(orderDeal_300391);

                break;
            // 债券现券交易竞买成交逐笔行情
            case 300491:
                OrderDeal_300491 orderDeal_300491 = new OrderDeal_300491();
                orderDeal_300491.read(message_body);
                orderDeal_300491.toStrings();
//                outputStream.writeObject(orderDeal_300491);
                break;

        }


    }

    public static int isMessage(long msgType){
        switch ((int) msgType){
            // 登陆消息
            case 1:
                return 1;
            // 注销消息
            case 2:
                return 2;
            // 心跳消息
            case 3:
                return 3;
            // 频道心跳
            case 390095:
                return 390095;
            // 重传消息
            case 390094:
                return 390094;
            // 用户信息报告消息
            case 390093:
                return 390093;
            // 快照行情频道统计
            case 390090:
                return 390090;
            // 业务拒绝消息
            case 8:
                return 8;

            // 市场实时状态
            case 390019:
                return 390019;
            // 证券实时状态
            case 390013:
                return 390013;
            // 公告
            case 390012:
                return 390012;
            // 现货（股票，基金等）集中竞价交易快照行情 期权集中竞价交易快照行情
            case 300111:
                return 300111;
            // 债券通用质押式回购匹配交易快照行情  债券分销快照行情 债券现券交易快照行情
            case 300211:
                return 300211;
            // 以收盘价交易的盘后定价大宗交易快照行情 以成交量加权平均价交易的盘后定价大宗交易快照行情
            case 300611:
                return 300611;
            // 盘后定价交易快照行情
            case 303711:
                return 303711;
            // 港股实时行情
            case 306311:
                return 306311;
            // 指数快照行情 国证指数快照行情
            case 309011:
                return 309011;
            // 成交量统计指标快照行情
            case 309111:
                return 309111;

            // 现货（股票，基金，债券等）集中竞价交易逐笔行情
            case 300192:
                return 300192;
            // 协议交易逐笔意向行情
            case 300592:
                return 300592;
            // 转融通证券出借逐笔行情
            case 300792:
                return 300792;
            // 债券现券交易匹配成交逐笔行情
            case 300292:
                return 300292;
            // 债券现券交易点击成交逐笔行情
            case 300392:
                return 300392;
            // 债券现券交易竞买成交逐笔行情
            case 300492:
                return 300492;


            // 现货（股票，基金，债券等）集中竞价交易逐笔行情
            case 300191:
                return 300191;
            // 协议交易逐笔意向行情
            case 300591:
                return 300591;
            // 转融通证券出借逐笔行情
            case 300791:
                return 300791;
            // 债券现券交易匹配成交逐笔行情
            case 300391:
                return 300391;
            // 债券现券交易竞买成交逐笔行情
            case 300491:
                return 300491;
            default:
                return -1;
        }
    }
}
