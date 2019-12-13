package com.wugui.dataxweb.log;

public class LogConstant {

    /** 日志类型 */
    // 登录
    public static final short TYPE_LOGIN = (short) 0;
    // 退出
    public static final short TYPE_LOGOUT = (short) 1;
    // 访问
    public static final short TYPE_VISIT = (short) 2;
    // 新增
    public static final short TYPE_ADD = (short) 3;
    // 更新
    public static final short TYPE_UPDATE = (short) 4;
    // 删除
    public static final short TYPE_DELETE = (short) 5;
    // 外链
    public static final short TYPE_EXTERNAL_LINK = (short) 6;
    // 导入
    public static final short TYPE_IMPORTS = (short) 7;
    // 导出
    public static final short TYPE_EXPORT = (short) 8;
    // 导出/打印
    public static final short TYPE_EXPORT_PRINT = (short) 9;

    /** 模块路径-账套外 */
    public static final String LOGIN = "系统登录";
    public static final String LOGOUT = "系统退出";
    public static final String PERSONAL_INFO_BASE_INFO = "个人信息设置/基本信息";
    public static final String PERSONAL_INFO_CHANGE_PASSWORD = "个人信息设置/修改密码";
    public static final String WUKONGCAISHUI = "悟空财税";
    public static final String HELP = "帮助";
    public static final String CUSTOMER_MANAGEMENT = "客户管理";
    public static final String CUSTOMER_MANAGEMENT_SERVER_CUSTOMER = "客户管理/服务中客户";
    public static final String CUSTOMER_MANAGEMENT_SEALED_CUSTOMER = "客户管理/已封存客户";
    public static final String SYSTEM_MANAGEMENT_ORGANIZATION = "系统管理/组织机构";
    public static final String SYSTEM_MANAGEMENT_ORGANIZATION_EMPLOYEE_MANAGEMENT = "系统管理/组织机构/员工管理";
    public static final String SYSTEM_MANAGEMENT_ROLE_PERMISSION = "系统管理/岗位权限";
    public static final String SYSTEM_MANAGEMENT_BASE_PARAMETER = "系统管理/基础参数";

    /** 模块路径-账套内 */
    public static final String HOME_PAGE = "首页";
    public static final String VOUCHER_ADD = "凭证录入";
    public static final String VOUCHER_INFO = "凭证信息";
    public static final String VOUCHER_QUERY = "凭证查询";
    public static final String ACCOUNT_BOOK_GENERAL_LEDGER = "账簿/总账";
    public static final String ACCOUNT_BOOK_SUBSIDIARY_LEDGER = "账簿/明细账";
    public static final String ACCOUNT_BOOK_ACCOUNT_BALANCE_SHEET = "账簿/科目余额表";
    public static final String ACCOUNT_BOOK_ACCOUNT_SUMMARY = "账簿/科目汇总表";
    public static final String REPORT_PROFIT = "报表/利润表";
    public static final String REPORT_CASH_FLOW = "报表/现金流量表";
    public static final String REPORT_ASSETS_DEBT = "报表/资产负债表";
    public static final String CHECKOUT = "结账";
    public static final String ACCOUNT = "科目";
    public static final String BEGINNING = "期初";
    public static final String ASSIST_CUSTOMER = "辅助核算/客户";
    public static final String ASSIST_SUPPLIER = "辅助核算/供应商";
    public static final String ASSIST_EMPLOYEE = "辅助核算/员工";
    public static final String ASSIST_DEPARTMENT = "辅助核算/部门";
    public static final String ASSIST_PROJECT = "辅助核算/项目";
    public static final String ASSIST_STOCK = "辅助核算/存货及服务";
    /** 资产 */
    public static final String FIXED_ASSETS = "资产/固定资产";
    public static final String FIXED_ASSETS_CATEGORY = "资产/固定资产/类别设置";
    public static final String INTANGIBLE_ASSETS = "资产/无形资产";
    public static final String INTANGIBLE_ASSETS_CATEGORY = "资产/无形资产/类别设置";
    public static final String UN_AMORTIZE_ASSETS = "资产/长期待摊费用";
    public static final String DETAIL_REPORT_FOR_FIXED_ASSETS_DEPRE = "折旧摊销明细表/固定资产";
    public static final String DETAIL_REPORT_FOR_INTANGIBLE_ASSETS_DEPRE = "折旧摊销明细表/无形资产";
    public static final String DETAIL_REPORT_FOR_UN_AMORTIZE_ASSETS_DEPRE = "折旧摊销明细表/长期待摊费用";
    public static final String SUMMARY_REPORT_FOR_FIXED_ASSETS_DEPRE = "折旧摊销汇总表/固定资产";
    public static final String SUMMARY_REPORT_FOR_INTANGIBLE_ASSETS_DEPRE = "折旧摊销汇总表/无形资产";
    public static final String CLEAR_FOR_FIXED_ASSETS = "资产清理处置/固定资产";
    public static final String CLEAR_FOR_INTANGIBLE_ASSETS = "资产清理处置/无形资产";
    public static final String CLEAR_FOR_UN_AMORTIZE_ASSETS = "资产清理处置/长期待摊费用";
    /** 合同 */
    public static final String CONTRACT_NORMAL = "合同管理/常规合同";
    public static final String CONTRACT_OVERDUE = "合同管理/到期合同";
    public static final String CONTRACT_INVALID = "合同管理/作废合同";

    /**发票*/
    public static final String INVOICE_ADD = "发票/进项发票&销项发票";
    public static final String INVOICE_IMPORT = "发票/进项发票&销项发票";
    public static final String INVOICE_UPDATE = "发票/进项发票&销项发票";
    public static final String INVOICE_VOUCHER = "发票/进项发票&销项发票";
    public static final String INVOICE_DEDUCT = "发票/进项发票&销项发票";
    public static final String INVOICE_NO_DEDUCT = "发票/进项发票&销项发票";
    public static final String INVOICE_BATCH_GENERATE_VOUCHER = "发票/进项发票&销项发票";
    public static final String INVOICE_GENERATE_FUND = "发票/进项发票&销项发票";
    public static final String INVOICE_BATCH_GENERATE_FUND = "发票/进项发票&销项发票";
    public static final String INVOICE_DELETE = "发票/进项发票&销项发票";

    /**
     * 费用
     */
    public static final String COST_DELETE = "发票/费用";
    public static final String COST_ADD = "发票/费用";
    public static final String COST_UPDATE = "发票/费用";
    public static final String COST_VOUCHER = "发票/费用";
    public static final String COST_BATCH_GENERATE_VOUCHER = "发票/费用";

    /**
     * 资金
     */
    public static final String FUND_ADD = "资金";
    public static final String FUND_IMPORT = "资金";
    public static final String FUND_UPDATE = "资金";
    public static final String FUND_VOUCHER = "资金";
    public static final String FUND_BATCH_GENERATE_VOUCHER_TO_BANK = "资金";
    public static final String FUND_BATCH_GENERATE_VOUCHER_TO_CASH = "资金";
    public static final String FUND_BATCH_GENERATE_VOUCHER_TO_OTHER_CASH = "资金";
    public static final String FUND_DELETE = "资金/删除";

    /**
     * 单据类型
     */
    public static final String DOCUMENT_TYPE_ADD = "单据类型设置";
    public static final String DOCUMENT_TYPE_UPDATE = "单据类型设置";
    public static final String DOCUMENT_TYPE_DELETE = "单据类型设置";

    /**
     * 银行
     */
    public static final String BANK_ADD = "设置/银行";
    public static final String BANK_UPDATE = "设置/银行";
    public static final String BANK_DELETE = "设置/银行";
}
