package com.yada.mock

object GCSMockConfig {

  val regex_transactionID = """[\s\S]*transactionID=\"([0-9]+)[\s\S]*""".r
  val regex_cardNo = """[\s\S]*cardNo\" value=\"([0-9]+)[\s\S]*""".r
  val regex_Currency = """[\s\S]*currencyCode\" value=\"([0-9]+)[\s\S]*""".r
  val regex_billingSummary = """[\s\S]*statementNo\" value=\"([0-9]+)[\s\S]*accountId\" value=\"([0-9a-zA-Z]+)[\s\S]*""".r
  val getBalanceTranID = "410103"
  val getBillingPeriodsTranID = "010301"
  val getBillingSummaryTranID = "010302"
  val getCurrencyCodesTranID = "010102"

  val balanceResult = Map {
    "5149580068840943CNY" -> "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"410103\" isRequest=\"false\" isResponse=\"true\">\n    <system>\n        <prop key=\"transactionCode\" value=\"410103\"/>\n        <prop key=\"transactionNumber\" value=\"0020160318180600\"/>\n        <prop key=\"txnTraceNumber\" value=\"0011503706\"/>\n        <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/>\n        <prop key=\"requestChannelId\" value=\"WX01\"/>\n        <prop key=\"txnBankCode\" value=\"003\"/>\n        <prop key=\"txnProvinceCode\" value=\"\"/>\n        <prop key=\"txnBranchCode\" value=\"00003\"/>\n        <prop key=\"txnCounterCode\" value=\"\"/>\n        <prop key=\"txnTerminalCode\" value=\"\"/>\n        <prop key=\"txnUserCode\" value=\"wx4101031\"/>\n        <prop key=\"localBankTxnRequestTime\" value=\"18:06:13\"/>\n        <prop key=\"localBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"localBankTxnResponseTime\" value=\"18:06:14\"/>\n        <prop key=\"localBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnRequestTime\" value=\"18:06:13\"/>\n        <prop key=\"bocBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnResponseTime\" value=\"18:06:14\"/>\n        <prop key=\"bocBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"returnCode\" value=\"+GC00000\"/>\n        <prop key=\"returnMessage\" value=\"Success\"/>\n    </system>\n    <page key=\"RP010054\">\n        <prop key=\"cardNo\" value=\"5149580068840943\"/>\n        <prop key=\"currencyCode\" value=\"CNY\"/>\n        <prop key=\"currAccountAmount\" value=\"-00000000006818600\"/>\n        <prop key=\"loanBalanceLimit\" value=\"-00000000008054600\"/>\n        <prop key=\"periodAvailbleCreditLimit\" value=\"+00000000011945400\"/>\n        <prop key=\"wholeCreditLimit\" value=\"+00000000020000000\"/>\n        <prop key=\"preCashAdvanceCreditLimit\" value=\"+00000000006000000\"/>\n        <prop key=\"instalmentPlanAvailableLimit\" value=\"+00000000011945400\"/>\n    </page>\n</GCS>"
    "5149580068840943USD" -> "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"410103\" isRequest=\"false\" isResponse=\"true\">\n    <system>\n        <prop key=\"transactionCode\" value=\"410103\"/>\n        <prop key=\"transactionNumber\" value=\"0020160318181000\"/>\n        <prop key=\"txnTraceNumber\" value=\"0011503707\"/>\n        <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/>\n        <prop key=\"requestChannelId\" value=\"WX01\"/>\n        <prop key=\"txnBankCode\" value=\"003\"/>\n        <prop key=\"txnProvinceCode\" value=\"\"/>\n        <prop key=\"txnBranchCode\" value=\"00003\"/>\n        <prop key=\"txnCounterCode\" value=\"\"/>\n        <prop key=\"txnTerminalCode\" value=\"\"/>\n        <prop key=\"txnUserCode\" value=\"wx4101032\"/>\n        <prop key=\"localBankTxnRequestTime\" value=\"18:08:51\"/>\n        <prop key=\"localBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"localBankTxnResponseTime\" value=\"18:08:51\"/>\n        <prop key=\"localBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnRequestTime\" value=\"18:08:51\"/>\n        <prop key=\"bocBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnResponseTime\" value=\"18:08:51\"/>\n        <prop key=\"bocBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"returnCode\" value=\"+GC00000\"/>\n        <prop key=\"returnMessage\" value=\"Success\"/>\n    </system>\n    <page key=\"RP010054\">\n        <prop key=\"cardNo\" value=\"5149580068840943\"/>\n        <prop key=\"currencyCode\" value=\"USD\"/>\n        <prop key=\"currAccountAmount\" value=\"+00000000000000000\"/>\n        <prop key=\"loanBalanceLimit\" value=\"+00000000000000000\"/>\n        <prop key=\"periodAvailbleCreditLimit\" value=\"+00000000001994880\"/>\n        <prop key=\"wholeCreditLimit\" value=\"+00000000002000000\"/>\n        <prop key=\"preCashAdvanceCreditLimit\" value=\"+00000000000600000\"/>\n        <prop key=\"instalmentPlanAvailableLimit\" value=\"+00000000001994880\"/>\n    </page>\n</GCS>"
  }
  val billingPeriodsResult = Map {
    "5149580068840943" -> "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"010301\" isRequest=\"false\" isResponse=\"true\">\n    <system>\n        <prop key=\"transactionCode\" value=\"010301\"/>\n        <prop key=\"transactionNumber\" value=\"0020160318182100\"/>\n        <prop key=\"txnTraceNumber\" value=\"0011503708\"/>\n        <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/>\n        <prop key=\"requestChannelId\" value=\"WX01\"/>\n        <prop key=\"txnBankCode\" value=\"003\"/>\n        <prop key=\"txnProvinceCode\" value=\"\"/>\n        <prop key=\"txnBranchCode\" value=\"00003\"/>\n        <prop key=\"txnCounterCode\" value=\"\"/>\n        <prop key=\"txnTerminalCode\" value=\"\"/>\n        <prop key=\"txnUserCode\" value=\"wx010301\"/>\n        <prop key=\"localBankTxnRequestTime\" value=\"18:20:09\"/>\n        <prop key=\"localBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"localBankTxnResponseTime\" value=\"18:20:09\"/>\n        <prop key=\"localBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnRequestTime\" value=\"18:20:09\"/>\n        <prop key=\"bocBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnResponseTime\" value=\"18:20:09\"/>\n        <prop key=\"bocBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"returnCode\" value=\"+GC00000\"/>\n        <prop key=\"returnMessage\" value=\"Success\"/>\n    </system>\n    <page key=\"RP010301\">\n        <prop key=\"cardNo\" value=\"5149580068840943\"/>\n        <list key=\"RP010302\" entityKey=\"statementSummaryList\" size=\"3\">\n            <entity>\n                <prop key=\"accountId\" value=\"001A0213064FF77E\"/>\n                <prop key=\"currencyCode\" value=\"CNY\"/>\n                <prop key=\"periodEndDate\" value=\"2021-07-10\"/>\n                <prop key=\"periodStartDate\" value=\"2021-06-11\"/>\n                <prop key=\"statementNo\" value=\"3\"/>\n            </entity>\n            <entity>\n                <prop key=\"accountId\" value=\"001A0213064FF77E\"/>\n                <prop key=\"currencyCode\" value=\"CNY\"/>\n                <prop key=\"periodEndDate\" value=\"2021-06-10\"/>\n                <prop key=\"periodStartDate\" value=\"2021-05-11\"/>\n                <prop key=\"statementNo\" value=\"2\"/>\n            </entity>\n            <entity>\n                <prop key=\"accountId\" value=\"001A0213064FF77E\"/>\n                <prop key=\"currencyCode\" value=\"CNY\"/>\n                <prop key=\"periodEndDate\" value=\"2021-05-10\"/>\n                <prop key=\"periodStartDate\" value=\"2020-03-10\"/>\n                <prop key=\"statementNo\" value=\"1\"/>\n            </entity>\n        </list>\n    </page>\n</GCS>"
  }

  val billingSummaryResult = Map {
    "1001A0213064FF77E" -> "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"010302\" isRequest=\"false\" isResponse=\"true\">\n    <system>\n        <prop key=\"transactionCode\" value=\"010302\"/>\n        <prop key=\"transactionNumber\" value=\"0020160318182500\"/>\n        <prop key=\"txnTraceNumber\" value=\"0011503710\"/>\n        <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/>\n        <prop key=\"requestChannelId\" value=\"WX01\"/>\n        <prop key=\"txnBankCode\" value=\"003\"/>\n        <prop key=\"txnProvinceCode\" value=\"\"/>\n        <prop key=\"txnBranchCode\" value=\"00003\"/>\n        <prop key=\"txnCounterCode\" value=\"\"/>\n        <prop key=\"txnTerminalCode\" value=\"\"/>\n        <prop key=\"txnUserCode\" value=\"wx0103020\"/>\n        <prop key=\"localBankTxnRequestTime\" value=\"18:28:50\"/>\n        <prop key=\"localBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"localBankTxnResponseTime\" value=\"18:28:50\"/>\n        <prop key=\"localBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnRequestTime\" value=\"18:28:50\"/>\n        <prop key=\"bocBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnResponseTime\" value=\"18:28:50\"/>\n        <prop key=\"bocBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"returnCode\" value=\"+GC00000\"/>\n        <prop key=\"returnMessage\" value=\"Success\"/>\n    </system>\n    <page key=\"RP010302\">\n        <prop key=\"periodStartDate\" value=\"2020-03-10\"/>\n        <prop key=\"periodEndDate\" value=\"2021-05-10\"/>\n        <prop key=\"currencyCode\" value=\"CNY\"/>\n        <prop key=\"upPeriodBalance\" value=\"+00000000000000000\"/>\n        <prop key=\"closingBalance\" value=\"+00000000004923000\"/>\n        <prop key=\"closingMinPayment\" value=\"+00000000001213000\"/>\n        <prop key=\"closingPastDue\" value=\"+00000000000000000\"/>\n        <prop key=\"paymentDueDate\" value=\"2021-05-30\"/>\n        <prop key=\"exchangeRateOnStatementDay\" value=\"\"/>\n        <prop key=\"upPeriodPaymentAmount\" value=\"+00000000000000000\"/>\n        <prop key=\"currPeriodDebitsAmount\" value=\"+00000000004923000\"/>\n        <prop key=\"minPaymentAmount\" value=\"+00000000001213000\"/>\n    </page>\n</GCS>"
    "2001A0213064FF77E" -> "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"010302\" isRequest=\"false\" isResponse=\"true\">\n    <system>\n        <prop key=\"transactionCode\" value=\"010302\"/>\n        <prop key=\"transactionNumber\" value=\"0020160318183200\"/>\n        <prop key=\"txnTraceNumber\" value=\"0011503711\"/>\n        <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/>\n        <prop key=\"requestChannelId\" value=\"WX01\"/>\n        <prop key=\"txnBankCode\" value=\"003\"/>\n        <prop key=\"txnProvinceCode\" value=\"\"/>\n        <prop key=\"txnBranchCode\" value=\"00003\"/>\n        <prop key=\"txnCounterCode\" value=\"\"/>\n        <prop key=\"txnTerminalCode\" value=\"\"/>\n        <prop key=\"txnUserCode\" value=\"wx0103022\"/>\n        <prop key=\"localBankTxnRequestTime\" value=\"18:30:22\"/>\n        <prop key=\"localBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"localBankTxnResponseTime\" value=\"18:30:23\"/>\n        <prop key=\"localBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnRequestTime\" value=\"18:30:22\"/>\n        <prop key=\"bocBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnResponseTime\" value=\"18:30:23\"/>\n        <prop key=\"bocBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"returnCode\" value=\"+GC00000\"/>\n        <prop key=\"returnMessage\" value=\"Success\"/>\n    </system>\n    <page key=\"RP010302\">\n        <prop key=\"periodStartDate\" value=\"2021-05-11\"/>\n        <prop key=\"periodEndDate\" value=\"2021-06-10\"/>\n        <prop key=\"currencyCode\" value=\"CNY\"/>\n        <prop key=\"upPeriodBalance\" value=\"+00000000004923000\"/>\n        <prop key=\"closingBalance\" value=\"+00000000005385140\"/>\n        <prop key=\"closingMinPayment\" value=\"+00000000000561000\"/>\n        <prop key=\"closingPastDue\" value=\"+00000000000000000\"/>\n        <prop key=\"paymentDueDate\" value=\"2021-06-30\"/>\n        <prop key=\"exchangeRateOnStatementDay\" value=\"\"/>\n        <prop key=\"upPeriodPaymentAmount\" value=\"+00000000004923000\"/>\n        <prop key=\"currPeriodDebitsAmount\" value=\"+00000000005385140\"/>\n        <prop key=\"minPaymentAmount\" value=\"+00000000000561000\"/>\n    </page>\n</GCS>"
    "3001A0213064FF77E" -> "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"010302\" isRequest=\"false\" isResponse=\"true\">\n    <system>\n        <prop key=\"transactionCode\" value=\"010302\"/>\n        <prop key=\"transactionNumber\" value=\"0020160318183300\"/>\n        <prop key=\"txnTraceNumber\" value=\"0011503712\"/>\n        <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/>\n        <prop key=\"requestChannelId\" value=\"WX01\"/>\n        <prop key=\"txnBankCode\" value=\"003\"/>\n        <prop key=\"txnProvinceCode\" value=\"\"/>\n        <prop key=\"txnBranchCode\" value=\"00003\"/>\n        <prop key=\"txnCounterCode\" value=\"\"/>\n        <prop key=\"txnTerminalCode\" value=\"\"/>\n        <prop key=\"txnUserCode\" value=\"wx0103023\"/>\n        <prop key=\"localBankTxnRequestTime\" value=\"18:31:22\"/>\n        <prop key=\"localBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"localBankTxnResponseTime\" value=\"18:31:22\"/>\n        <prop key=\"localBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnRequestTime\" value=\"18:31:22\"/>\n        <prop key=\"bocBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnResponseTime\" value=\"18:31:22\"/>\n        <prop key=\"bocBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"returnCode\" value=\"+GC00000\"/>\n        <prop key=\"returnMessage\" value=\"Success\"/>\n    </system>\n    <page key=\"RP010302\">\n        <prop key=\"periodStartDate\" value=\"2021-06-11\"/>\n        <prop key=\"periodEndDate\" value=\"2021-07-10\"/>\n        <prop key=\"currencyCode\" value=\"CNY\"/>\n        <prop key=\"upPeriodBalance\" value=\"+00000000005385140\"/>\n        <prop key=\"closingBalance\" value=\"+00000000006818600\"/>\n        <prop key=\"closingMinPayment\" value=\"+00000000000882000\"/>\n        <prop key=\"closingPastDue\" value=\"+00000000000561000\"/>\n        <prop key=\"paymentDueDate\" value=\"2021-07-30\"/>\n        <prop key=\"exchangeRateOnStatementDay\" value=\"\"/>\n        <prop key=\"upPeriodPaymentAmount\" value=\"+00000000000000000\"/>\n        <prop key=\"currPeriodDebitsAmount\" value=\"+00000000001433460\"/>\n        <prop key=\"minPaymentAmount\" value=\"+00000000001443000\"/>\n    </page>\n</GCS>"
  }

  val currencyCodesResult = Map {
    "5149580068840943" -> "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"010102\" isRequest=\"false\" isResponse=\"true\">\n    <system>\n        <prop key=\"transactionCode\" value=\"010102\"/>\n        <prop key=\"transactionNumber\" value=\"0020160318175400\"/>\n        <prop key=\"txnTraceNumber\" value=\"0011503704\"/>\n        <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/>\n        <prop key=\"requestChannelId\" value=\"WX01\"/>\n        <prop key=\"txnBankCode\" value=\"003\"/>\n        <prop key=\"txnProvinceCode\" value=\"\"/>\n        <prop key=\"txnBranchCode\" value=\"00003\"/>\n        <prop key=\"txnCounterCode\" value=\"\"/>\n        <prop key=\"txnTerminalCode\" value=\"\"/>\n        <prop key=\"txnUserCode\" value=\"wx010102\"/>\n        <prop key=\"localBankTxnRequestTime\" value=\"17:55:23\"/>\n        <prop key=\"localBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"localBankTxnResponseTime\" value=\"17:55:24\"/>\n        <prop key=\"localBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnRequestTime\" value=\"17:55:23\"/>\n        <prop key=\"bocBankTxnRequestDate\" value=\"2016-03-18\"/>\n        <prop key=\"bocBankTxnResponseTime\" value=\"17:55:24\"/>\n        <prop key=\"bocBankTxnResponseDate\" value=\"2016-03-18\"/>\n        <prop key=\"returnCode\" value=\"+GC00000\"/>\n        <prop key=\"returnMessage\" value=\"Success\"/>\n    </system>\n    <page key=\"RP010102\">\n        <list key=\"RP010101\" entityKey=\"accountList\" size=\"2\">\n            <entity>\n                <prop key=\"accountId\" value=\"001A0213064FF77E\"/>\n                <prop key=\"accountRefNo\" value=\"2861695039487302\"/>\n                <prop key=\"automaticStatusCode\" value=\"\"/>\n                <prop key=\"automaticStatusSetDate\" value=\"\"/>\n                <prop key=\"currencyCode\" value=\"CNY\"/>\n                <prop key=\"institutionId\" value=\"BOCK\"/>\n                <prop key=\"issuingBranchId\" value=\"\"/>\n                <prop key=\"manualStatusCode\" value=\"NORM\"/>\n                <prop key=\"manualStatusSetDate\" value=\"\"/>\n                <prop key=\"productType\" value=\"PRMC\"/>\n            </entity>\n            <entity>\n                <prop key=\"accountId\" value=\"001A0213064FF780\"/>\n                <prop key=\"accountRefNo\" value=\"7821440695701839\"/>\n                <prop key=\"automaticStatusCode\" value=\"\"/>\n                <prop key=\"automaticStatusSetDate\" value=\"\"/>\n                <prop key=\"currencyCode\" value=\"USD\"/>\n                <prop key=\"institutionId\" value=\"BOCK\"/>\n                <prop key=\"issuingBranchId\" value=\"\"/>\n                <prop key=\"manualStatusCode\" value=\"NORM\"/>\n                <prop key=\"manualStatusSetDate\" value=\"\"/>\n                <prop key=\"productType\" value=\"PRMC\"/>\n            </entity>\n        </list>\n    </page>\n</GCS>"
  }
}