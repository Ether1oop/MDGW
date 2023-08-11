
CREATE DATABASE IF NOT EXISTS mdgw;

CREATE TABLE IF NOT EXISTS mdgw.message_300111(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	TradingPhaseCode varchar(8),
	PrevClosePx Int64,
	NumTrades Int64,
	TotalVolumeTrade Int64,
	TotalValueTrade Int64,
	
	NoMDEntries UInt32,
	MDEntryType Nullable(varchar(3)),
	MDEntryPx Nullable(Int64),
	MDEntrySize Nullable(Int64),
	MDPriceLevel Nullable(UInt16),
	NumberOfOrders Nullable(Int64),
	NoOrders Nullable(UInt32),
	OrderQty Nullable(Int64)
)
ENGINE = MergeTree()
ORDER BY (OrigTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300211(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	TradingPhaseCode varchar(8),
	PrevClosePx Int64,
	NumTrades Int64,
	TotalVolumeTrade Int64,
	TotalValueTrade Int64,
	NoMDEntries Nullable(UInt32),
	
	MDEntryType Nullable(varchar(3)),
	MDEntryPx Nullable(Int64),
	MDEntrySize Nullable(Int64),
	MDPriceLevel Nullable(UInt16),
	NumberOfOrders Nullable(Int64),
	NoOrders Nullable(UInt32),
	OrderQty Nullable(Int64),
	
	NoSubTradingPhaseCodes Nullable(UInt32),
	SubTradingPhaseCode Nullable(varchar(8)),
	TradingType Nullable(UInt8),
	AuctionVolumeTrade Nullable(Int64),
	AuctionValueTrade Nullable(Int64)
)
ENGINE = MergeTree()
ORDER BY (OrigTime);

CREATE TABLE IF NOT EXISTS mdgw.message_300611(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	TradingPhaseCode varchar(8),
	PrevClosePx Int64,
	NumTrades Int64,
	TotalVolumeTrade Int64,
	TotalValueTrade Int64,
	NoMDEntries UInt32,
	
	MDEntryType Nullable(varchar(3)),
	MDEntryPx Nullable(Int64),
	MDEntrySize Nullable(Int64)
)
ENGINE = MergeTree()
ORDER BY (OrigTime);

CREATE TABLE IF NOT EXISTS mdgw.message_303711(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	TradingPhaseCode varchar(8),
	PrevClosePx Int64,
	NumTrades Int64,
	TotalVolumeTrade Int64,
	TotalValueTrade Int64,
	NoMDEntries UInt32,
	
	MDEntryType Nullable(varchar(3)),
	MDEntryPx Nullable(Int64),
	MDEntrySize Nullable(Int64)
)
ENGINE = MergeTree()
ORDER BY (OrigTime);

CREATE TABLE IF NOT EXISTS mdgw.message_306311(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	TradingPhaseCode varchar(8),
	PrevClosePx Int64,
	NumTrades Int64,
	TotalVolumeTrade Int64,
	TotalValueTrade Int64,
	NoMDEntries UInt32,
	
	MDEntryType Nullable(varchar(3)),
	MDEntryPx Nullable(Int64),
	MDEntrySize Nullable(Int64),
	MDPriceLevel Nullable(UInt16),
	NoComplexEventTimes UInt32,
	ComplexEventStartTime Nullable(DateTime64(3, 'Asia/Shanghai')),
	ComplexEventEndTime Nullable(DateTime64(3, 'Asia/Shanghai'))
)
ENGINE = MergeTree()
ORDER BY (OrigTime);

CREATE TABLE IF NOT EXISTS mdgw.message_309011(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	TradingPhaseCode varchar(8),
	PrevClosePx Int64,
	NumTrades Int64,
	TotalVolumeTrade Int64,
	TotalValueTrade Int64,
	NoMDEntries UInt32,
	
	MDEntryType Nullable(varchar(3)),
	MDEntryPx Nullable(Int64)
)
ENGINE = MergeTree()
ORDER BY (OrigTime);

CREATE TABLE IF NOT EXISTS mdgw.message_309111(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	TradingPhaseCode varchar(8),
	PrevClosePx Int64,
	NumTrades Int64,
	TotalVolumeTrade Int64,
	TotalValueTrade Int64,
	StockNum UInt32
)ENGINE = MergeTree()
ORDER BY (OrigTime);

CREATE TABLE IF NOT EXISTS mdgw.message_390019(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	MarketID varchar(8),
	MarketSegmentID varchar(8),
	TradingSessionID varchar(4),
	TradingSessionSubID varchar(4),
	TradSesStatus UInt16,
	TradSesStartTime DateTime64(3, 'Asia/Shanghai'),
	TradSesEndTime DateTime64(3, 'Asia/Shanghai'),
	ThresholdAmount Int64,
	PosAmt Int64,
	AmountStatus UInt8
)
ENGINE = MergeTree()
ORDER BY (OrigTime);

CREATE TABLE IF NOT EXISTS mdgw.message_300191(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	BidApplSeqNum Int64,
	OfferApplSeqNum Int64,
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	LastPx Int64,
	LastQty Int64,
	ExecType varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai')
)
ENGINE = MergeTree()
ORDER BY (TransactTime);

CREATE TABLE IF NOT EXISTS mdgw.message_300291(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	BidApplSeqNum Int64,
	OfferApplSeqNum Int64,
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	LastPx Int64,
	LastQty Int64,
	ExecType varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai')
)
ENGINE = MergeTree()
ORDER BY (TransactTime);

CREATE TABLE IF NOT EXISTS mdgw.message_300391(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	BidApplSeqNum Int64,
	OfferApplSeqNum Int64,
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	LastPx Int64,
	LastQty Int64,
	ExecType varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai'),
	
	SettlPeriod UInt8,
	SettlType UInt16
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300491(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	BidApplSeqNum Int64,
	OfferApplSeqNum Int64,
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	LastPx Int64,
	LastQty Int64,
	ExecType varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai'),
	
	SettlPeriod UInt8,
	SettlType UInt16,
	SecondaryOrderID varchar(16),
	BidExecInstType UInt16,
	MarginPrice Int64
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300591(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	BidApplSeqNum Int64,
	OfferApplSeqNum Int64,
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	LastPx Int64,
	LastQty Int64,
	ExecType varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai')
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300791(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	BidApplSeqNum Int64,
	OfferApplSeqNum Int64,
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	LastPx Int64,
	LastQty Int64,
	ExecType varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai')
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300192(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	Price Int64,
	OrderQty Int64,
	Side varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai'),
	
	OrdType varchar(1)
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300292(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	Price Int64,
	OrderQty Int64,
	Side varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai'),
	
	OrdType varchar(1)
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300392(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	Price Int64,
	OrderQty Int64,
	Side varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai'),
	
	QuoteID varchar(10),
	MemberID varchar(6),
	InvestorType varchar(2),
	InvestorID varchar(10),
	InvestorName varchar(120),
	TraderCode varchar(8),
	SettlPeriod UInt8,
	SettlType UInt16,
	Memo varchar(160)
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300492(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	Price Int64,
	OrderQty Int64,
	Side varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai'),
	
	MemberID varchar(6),
	InvestorType varchar(2),
	InvestorID varchar(10),
	InvestorName varchar(120),
	TraderCode varchar(8),
	SettlPeriod UInt8,
	SettlType UInt16,
	Memo varchar(160),
	
	SecondaryOrderID varchar(16),
	BidTransType UInt16,
	BidExecInstType UInt16,
	LowLimitPrice Int64,
	HighLimitPrice Int64,
	MinQty Int64,
	TradeDate UInt32
)
ENGINE = MergeTree()
ORDER BY (TransactTime);

CREATE TABLE IF NOT EXISTS mdgw.message_300592(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	Price Int64,
	OrderQty Int64,
	Side varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai'),
	
	ConfirmID varchar(8),
	Contactor varchar(12),
	ContactInfo varchar(30)
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_300792(
	ChannelNo UInt16,
	ApplSeqNum Int64,
	MDStreamID varchar(3),
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	Price Int64,
	OrderQty Int64,
	Side varchar(1),
	TransactTime DateTime64(3, 'Asia/Shanghai'),
	
	ExpirationDays UInt16, 
	ExpirationType UInt8
)
ENGINE = MergeTree()
ORDER BY (TransactTime);


CREATE TABLE IF NOT EXISTS mdgw.message_390013(
	OrigTime DateTime64(3, 'Asia/Shanghai'),
	ChannelNo UInt16,
	SecurityID varchar(8),
	SecurityIDSource varchar(4),
	FinancialStatus varchar(8),
	NoSwitch UInt32,
	SecuritySwitchType Nullable(UInt16),
	SecuritySwitchStatus Nullable(UInt16)
)
ENGINE = MergeTree()
ORDER BY (OrigTime);












