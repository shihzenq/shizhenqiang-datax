//package com.wugui.dataxweb.log;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.Date;
//import java.util.List;
//
//@Aspect
//@Component
//public class LogAspect {
//
//    private final String POINT_CUT = "execution(* com.wukongxiaozi.business.web.controller..*(..))";
//
//    private IAuthenticationFacade authenticationFacade;
//
//    private StringRedisTemplate template;
//
//    private AccountSetHolder accountSetHolder;
//
//    @Autowired
//    private OperateLogService operateLogService;
//
//    @Autowired
//    private AssetsService assetsService;
//
//    @Autowired
//    private AssetsCategoryService assetsCategoryService;
//
//    @Autowired
//    private ContractService contractService;
//
//    @Pointcut(POINT_CUT)
//    public void logPointCut() {
//    }
//
//    @SuppressWarnings("all")
//    @AfterReturning(value = POINT_CUT, returning = "keys")
//    public void afterReturning(JoinPoint joinPoint, Object keys) {
//
//        try {
//            Object[] args = joinPoint.getArgs();
//            String methodName = joinPoint.getSignature().getName();
//            ResponseData responseData = null;
//            if (keys instanceof ResponseData) {
//                responseData = (ResponseData) keys;
//            }
//            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//            Method method = methodSignature.getMethod();
//            com.wukongxiaozi.business.web.config.log.OperateLog operateLog =
//                    method.getAnnotation(com.wukongxiaozi.business.web.config.log.OperateLog.class);
//            // 打印导出没有返回值也需要记录日志
//            if (operateLog != null && (responseData == null || responseData.getCode() == 200)) {
//
//                String content = operateLog.content();
//                String path = operateLog.path();
//                short type = operateLog.type();
//                Short scope = operateLog.scope();
//                EnterpriseUser currentUser = authenticationFacade.getUser();
//                AccountSet accountSet = accountSetHolder.getCurrentAccountSet();
//                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//                HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//                if (request != null) {
//                    String ipAddress = getIpAddress(request);
//                    Class<?> clazz = method.getDeclaringClass();
//                    String className = clazz.getName();
//                    // 如果是批量操作，需要记录多条日志
//                    if (isContractController(className) && isBlankOutMethod(methodName)) {
//                        ContractBlankOutForm form = (ContractBlankOutForm) args[0];
//                        for (Long id : form.getIds()) {
//                            JSONObject data = (JSONObject) contractService.detail(id, currentUser);
//                            String code = data.getJSONObject("data").getString("code");
//                            content = "合同作废（编号：" + code + "）";
//                            com.wukongxiaozi.api.model.OperateLog log = packageLogModel(currentUser, accountSet, type, path,
//                                    content, ipAddress, scope);
//                            template.opsForList().rightPush(LOG_KEY, JSON.toJSONString(log));
//                        }
//                    } else if (isContractController(className) && isBatchDeleteMethod(methodName)) {
//                        List<String> codeList = (List<String>) responseData.getData();
//                        for (String code : codeList) {
//                            content = "删除合同（编号：" + code + "）";
//                            com.wukongxiaozi.api.model.OperateLog log = packageLogModel(currentUser, accountSet, type, path,
//                                    content, ipAddress, scope);
//                            template.opsForList().rightPush(LOG_KEY, JSON.toJSONString(log));
//                        }
//                    } else {
//                        content = generateContent(args, method, responseData, content, currentUser);
//                        com.wukongxiaozi.api.model.OperateLog log = packageLogModel(currentUser, accountSet, type, path,
//                                content, ipAddress, scope);
//                        template.opsForList().rightPush(LOG_KEY, JSON.toJSONString(log));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 根据方法名去拼接特定功能日志内容，有的日志内容是需要参数列表中的某个参数的
//     *
//     * @param args         请求参数
//     * @param method       方法
//     * @param responseData 方法的返回值
//     * @return 日志内容
//     */
//    private String generateContent(Object[] args, Method method, ResponseData responseData, String content, EnterpriseUser user) {
//        try {
//            String methodName = method.getName();
//            Class<?> clazz = method.getDeclaringClass();
//            String className = clazz.getName();
//            if (isFixedAssetsController(className) && isAddMethod(methodName)) {
//                FixedAssetsForm form = (FixedAssetsForm) args[0];
//                content = "新增固定资产" + form.getName() + "卡片";
//            } else if (isFixedAssetsController(className) && isUpdateMethod(methodName)) {
//                FixedAssetsForm form = (FixedAssetsForm) args[0];
//                content = "编辑固定资产" + form.getName() + "卡片";
//            } else if (isFixedAssetsController(className) && isClearMethod(methodName)) {
//                FixedAssetsClearForm form = (FixedAssetsClearForm) args[0];
//                Assets assets = assetsService.get(form.getId());
//                content = "清理固定资产" + assets.getName() + "卡片";
//            } else if (isFixedAssetsController(className) && isDeleteMethod(methodName)) {
//                String name = (String) responseData.getData();
//                content = "删除固定资产" + name + "卡片";
//            } else if (isFixedAssetsCategoryController(className) && isAddMethod(methodName)) {
//                AssetsCategoryForm form = (AssetsCategoryForm) args[0];
//                content = "新增类别" + form.getName();
//            } else if (isFixedAssetsCategoryController(className) && isUpdateMethod(methodName)) {
//                AssetsCategoryForm form = (AssetsCategoryForm) args[0];
//                content = "编辑类别" + form.getName();
//            } else if (isFixedAssetsCategoryController(className) && isDeleteMethod(methodName)) {
//                String name = (String) responseData.getData();
//                content = "删除类别" + name;
//            } else if (isIntangibleAssetsController(className) && isAddMethod(methodName)) {
//                IntangibleForm form = (IntangibleForm) args[0];
//                content = "新增无形资产" + form.getName() + "卡片";
//            } else if (isIntangibleAssetsController(className) && isUpdateMethod(methodName)) {
//                IntangibleForm form = (IntangibleForm) args[0];
//                content = "编辑无形资产" + form.getName() + "卡片";
//            } else if (isIntangibleAssetsController(className) && isClearMethod(methodName)) {
//                IntangibleAssetsClearForm form = (IntangibleAssetsClearForm) args[0];
//                Assets assets = assetsService.get(form.getId());
//                content = "处置无形资产" + assets.getName() + "卡片";
//            } else if (isIntangibleAssetsController(className) && isDeleteMethod(methodName)) {
//                String name = (String) responseData.getData();
//                content = "删除无形资产" + name + "卡片";
//            } else if (isIntangibleAssetsCategoryController(className) && isAddMethod(methodName)) {
//                AssetsCategoryForm form = (AssetsCategoryForm) args[0];
//                content = "新增类别" + form.getName();
//            } else if (isIntangibleAssetsCategoryController(className) && isUpdateMethod(methodName)) {
//                AssetsCategoryForm form = (AssetsCategoryForm) args[0];
//                content = "编辑类别" + form.getName();
//            } else if (isIntangibleAssetsCategoryController(className) && isDeleteMethod(methodName)) {
//                Long categoryId = (Long) args[0];
//                AssetsCategory category = assetsCategoryService.get(categoryId);
//                content = "删除类别" + category.getName();
//            } else if (isUnAmortizedExpenseAssetsController(className) && isAddMethod(methodName)) {
//                UnAmortizeForm form = (UnAmortizeForm) args[0];
//                content = "新增长期待摊费用" + form.getName() + "卡片";
//            } else if (isUnAmortizedExpenseAssetsController(className) && isUpdateMethod(methodName)) {
//                UnAmortizeForm form = (UnAmortizeForm) args[0];
//                content = "编辑长期待摊费用" + form.getName() + "卡片";
//            } else if (isUnAmortizedExpenseAssetsController(className) && isClearMethod(methodName)) {
//                UnAmortizeAssetsClearForm form = (UnAmortizeAssetsClearForm) args[0];
//                Assets assets = assetsService.get(form.getId());
//                content = "处置长期待摊费用" + assets.getName() + "卡片";
//            } else if (isFixedAssetsClearController(className) && isRestoreMethod(methodName)) {
//                AssetsDeleteForm form = (AssetsDeleteForm) args[0];
//                Assets assets = assetsService.get(form.getId());
//                content = "还原固定资产" + assets.getName() + "卡片";
//            } else if (isIntangibleAssetsClearController(className) && isRestoreMethod(methodName)) {
//                AssetsDeleteForm form = (AssetsDeleteForm) args[0];
//                Assets assets = assetsService.get(form.getId());
//                content = "还原无形资产" + assets.getName() + "卡片";
//            } else if (isUnAmortizedExpenseAssetsClearController(className) && isRestoreMethod(methodName)) {
//                AssetsDeleteForm form = (AssetsDeleteForm) args[0];
//                Assets assets = assetsService.get(form.getId());
//                content = "还原长期待摊费用" + assets.getName() + "卡片";
//            } else if (isAntiCheckoutMethod(methodName)) {
//                Date period = (Date) args[0];
//                Integer month = DateUtil.getMonth(period);
//                content = "反结至" + month + "期";
//            } else if (isContractController(className) && isAddMethod(methodName)) {
//                ContractForm form = (ContractForm) args[0];
//                String code = form.getCode();
//                content = "新增合同（编号：" + code + "）";
//            } else if (isContractController(className) && isAddSigningMethod(methodName)) {
//                ContractForm form = (ContractForm) args[0];
//                String code = form.getCode();
//                content = "新增合同（编号：" + code + "）";
//            } else if (isContractController(className) && isUpdateMethod(methodName)) {
//                ContractForm form = (ContractForm) args[0];
//                String code = form.getCode();
//                content = "更新合同（编号：" + code + "）";
//            } else if (isContractController(className) && isUpdateSigning(methodName)) {
//                ContractForm form = (ContractForm) args[0];
//                String code = form.getCode();
//                content = "更新合同（编号：" + code + "）";
//            } else if (isContractController(className) && isRecoveryContractMethod(methodName)) {
//                ContractRecoveryForm form = (ContractRecoveryForm) args[0];
//                JSONObject data = (JSONObject) contractService.detail(form.getContractId(), user);
//                String code = data.getJSONObject("data").getString("code");
//                content = "恢复合同（编号：" + code + "）";
//            } else if (isInvoiceController(className) && isAddMethod(methodName)) {
//                content = "新增发票";
//            } else if (isInvoiceController(className) && isUpdateMethod(methodName)) {
//                content = "编辑发票信息";
//            } else if (isInvoiceController(className) && isImport(methodName)) {
//                content = "导入发票";
//            } else if (isInvoiceController(className) && isSyncVoucher(methodName)) {
//                content = "单张发票生成凭证";
//            } else if (isInvoiceController(className) && isDeduct(methodName)) {
//                InvoiceDeductForm form = (InvoiceDeductForm)args[0];
//                if (form.getIsDeduct()) {
//                    content = "发票抵扣";
//                } else {
//                    content = "发票不予抵扣";
//                }
//            } else if (isInvoiceController(className) && isNoDeduct(methodName)) {
//                content = "发票不予抵扣";
//            } else if (isInvoiceController(className) && isBatchGenerateVoucher(methodName)) {
//                content = "发票批量生成凭证";
//            } else if (isInvoiceController(className) && isGenerateFund(methodName)) {
//                content = "单张发票结算";
//            } else if (isInvoiceController(className) && isBatchGenerateFund(methodName)) {
//                content = "发票批量结算";
//            } else if (isInvoiceController(className) && isDeleteMethod(methodName)) {
//                InvoiceDeleteForm form = (InvoiceDeleteForm) args[0];
//                if (null != form) {
//                    List<Long> ids = form.getIds();
//                    if (ids.size() > 1) {
//                        content = "发票批量删除";
//                    } else {
//                        content = "单张发票删除";
//                    }
//                }
//            } else if (isCostController(className) && isAddMethod(methodName)) {
//                content = "新增费用票";
//            } else if (isCostController(className) && isUpdateMethod(methodName)) {
//                content = "编辑费用票信息";
//            } else if (isCostController(className) && isSyncVoucher(methodName)) {
//                content = "单张费用票生成凭证";
//            } else if (isCostController(className) && isBatchGenerateVoucher(methodName)) {
//                content = "费用票批量生成凭证";
//            } else if (isCostController(className) && isBatchDeleteMethodToCost(methodName)) {
//               CostBatchDeleteForm form =  (CostBatchDeleteForm) args[0];
//               if (null != form) {
//                   List<Long> ids = form.getIds();
//                   if (ids.size() > 1) {
//                       content = "费用票批量删除";
//                   } else {
//                       content = "费用票删除";
//                   }
//               }
//            } else if (isFundController(className) && isAddMethod(methodName)) {
//                content = "新增对账单";
//            } else if (isFundController(className) && isImportToBank(methodName)) {
//                content = "导入银行对账单";
//            } else if (isFundController(className) && isUpdateMethod(methodName)) {
//                content = "编辑对账单信息";
//            } else if (isFundController(className) && isSyncVoucher(methodName)) {
//                content = "单张对账单生成凭证";
//            } else if (isFundController(className) && isDeleteMethod(methodName)) {
//                FundDeleteForm form = (FundDeleteForm) args[0];
//                List<Long> ids = form.getIds();
//                if (ids.size() > 1) {
//                    content = "对账单批量删除";
//                } else {
//                    content = "对账单删除";
//                }
//            } else if (isFundController(className) && isBatchGenerateVoucherToFundBank(methodName)) {
//                content = "银行对账单批量生成凭证";
//            } else if (isFundController(className) && isBatchGenerateVoucherToFundCash(methodName)) {
//                content = "现金对账单批量生成凭证";
//            } else if (isFundController(className) && isBatchGenerateVoucherToFundOtherCash(methodName)) {
//                content = "其他货币资金对账单批量生成凭证";
//            } else if (isDocumentController(className) && isAddMethod(methodName)) {
//                content = "新增业务类型";
//            } else if (isDocumentController(className) && isUpdateMethod(methodName)) {
//                content = "编辑业务类型";
//            } else if (isDocumentController(className) && isDeleteMethod(methodName)){
//                content = "删除业务类型";
//            } else if (isBankController(className) && isAddMethod(methodName)) {
//                content = "新增银行信息";
//            } else if (isBankController(className) && isUpdateMethod(methodName)) {
//                content = "编辑银行信息";
//            } else if (isBankController(className) && isDeleteMethod(methodName)) {
//                content = "删除银行信息";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return content;
//        }
//        return content;
//    }
//
//    /**
//     * 银行
//     */
//    private boolean isBankController(String className) {
//        return "com.wukongxiaozi.business.web.controller.BankController".equalsIgnoreCase(className);
//    }
//
//
//    /**
//     *单据类型
//     */
//    private boolean isDocumentController(String className) {
//        return "com.wukongxiaozi.business.web.controller.DocumentTypeController".equalsIgnoreCase(className);
//    }
//
//    /**
//     *资金
//     */
//    private boolean isFundController(String className) {
//        return "com.wukongxiaozi.business.web.controller.FundController".equalsIgnoreCase(className);
//    }
//
//
//    /**
//     * 发票费用
//     */
//    private boolean isCostController(String className) {
//        return "com.wukongxiaozi.business.web.controller.CostController".equalsIgnoreCase(className);
//    }
//
//
//    /**
//     * 长期待摊费用处置
//     */
//    private boolean isUnAmortizedExpenseAssetsClearController(String className) {
//        return "com.wukongxiaozi.business.web.controller.UnAmortizedExpenseAssetsClearController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 无形资产处置
//     */
//    private boolean isIntangibleAssetsClearController(String className) {
//        return "com.wukongxiaozi.business.web.controller.IntangibleAssetsClearController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 固定资产清理
//     */
//    private boolean isFixedAssetsClearController(String className) {
//        return "com.wukongxiaozi.business.web.controller.FixedAssetsClearController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 长期待摊费用
//     */
//    private boolean isUnAmortizedExpenseAssetsController(String className) {
//        return "com.wukongxiaozi.business.web.controller.UnAmortizedExpenseAssetsController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 无形资产类别
//     */
//    private boolean isIntangibleAssetsCategoryController(String className) {
//        return "com.wukongxiaozi.business.web.controller.IntangibleAssetsCategoryController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 无形资产
//     */
//    private boolean isIntangibleAssetsController(String className) {
//        return "com.wukongxiaozi.business.web.controller.IntangibleAssetsController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 固定资产类别
//     */
//    private boolean isFixedAssetsCategoryController(String className) {
//        return "com.wukongxiaozi.business.web.controller.FixedAssetsCategoryController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 固定资产
//     */
//    private boolean isFixedAssetsController(String className) {
//        return "com.wukongxiaozi.business.web.controller.FixedAssetsController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 发票
//    */
//    private boolean isInvoiceController(String className) {
//        return "com.wukongxiaozi.business.web.controller.InvoiceController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 合同
//     */
//    private boolean isContractController(String className) {
//        return "com.wukongxiaozi.business.web.controller.ContractController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 合同收款记录
//     */
//    private boolean isContractPaymentRecordController(String className) {
//        return "com.wukongxiaozi.business.web.controller.ContractPaymentRecordController".equalsIgnoreCase(className);
//    }
//
//    /**
//     * 添加方法
//     */
//    private boolean isAddMethod(String methodName) {
//        return isTargetMethod("add", methodName);
//    }
//
//    /**
//     *导入发票
//     */
//    private boolean isImport(String methodName) {
//        return isTargetMethod("importInvoice", methodName);
//    }
//
//    /**
//     *抵扣
//     */
//    private boolean isDeduct(String methodName) {
//        return isTargetMethod("deduct", methodName);
//    }
//
//    /**
//     *不予抵扣
//     */
//    private boolean isNoDeduct(String methodName) {
//        return isTargetMethod("noDeduct", methodName);
//    }
//
//    /**
//     * 发票单张生成凭证
//     */
//    private boolean isSyncVoucher(String methodName) {
//        return isTargetMethod("syncVoucher", methodName);
//    }
//
//    /**
//     *发票批量生成凭证
//     */
//    private boolean isBatchGenerateVoucher(String methodName) {
//        return isTargetMethod("batchGenerateVoucher", methodName);
//    }
//
//    /**
//     *发票结算
//     */
//    private boolean isGenerateFund(String methodName) {
//        return isTargetMethod("generateFund", methodName);
//    }
//
//    /**
//     *发票批量结算
//     */
//    private boolean isBatchGenerateFund(String methodName) {
//        return isTargetMethod("batchGenerateFund", methodName);
//    }
//
//    /**
//     *银行批量生成凭证
//     */
//    private boolean isBatchGenerateVoucherToFundBank(String methodName) {
//        return isTargetMethod("batchBankGenerateVoucher", methodName);
//    }
//
//    /**
//     *现金批量生成凭证
//     */
//    private boolean isBatchGenerateVoucherToFundCash(String methodName) {
//        return isTargetMethod("batchCashGenerateVoucher", methodName);
//    }
//
//    /**
//     *其他货币资金批量生成凭证
//     */
//    private boolean isBatchGenerateVoucherToFundOtherCash(String methodName) {
//        return isTargetMethod("batchOtherCashGenerateVoucher", methodName);
//    }
//
//    /**
//     * 银行对账单
//     */
//    private boolean isImportToBank(String methodName) {
//        return isTargetMethod("importGeneralFund", methodName) || isTargetMethod("importAbcFund", methodName) ||
//                    isTargetMethod("importCcbFund", methodName) || isTargetMethod("importIcbcFund", methodName)
//                    || isTargetMethod("importBrcbFund", methodName) || isTargetMethod("importCmbFund", methodName)
//                    || isTargetMethod("importCibFund", methodName) || isTargetMethod("importCebFund", methodName);
//    }
//
//    /**
//     * 添加并签约方法
//     */
//    private boolean isAddSigningMethod(String methodName) {
//        return isTargetMethod("addSigning", methodName);
//    }
//
//    /**
//     * 合同作废方法
//     */
//    private boolean isBlankOutMethod(String methodName) {
//        return isTargetMethod("blankOut", methodName);
//    }
//
//    /**
//     * 编辑方法
//     */
//    private boolean isUpdateMethod(String methodName) {
//        return isTargetMethod("update", methodName);
//    }
//
//    /**
//     * 编辑并签约方法
//     */
//    private boolean isUpdateSigning(String methodName) {
//        return isTargetMethod("updateSigning", methodName);
//    }
//
//    /**
//     * 清理方法
//     */
//    private boolean isClearMethod(String methodName) {
//        return isTargetMethod("clear", methodName);
//    }
//
//    /**
//     * 删除方法
//     */
//    private boolean isDeleteMethod(String methodName) {
//        return isTargetMethod("delete", methodName);
//    }
//
//    /**
//     * 批量删除方法
//     */
//    private boolean isBatchDeleteMethod(String methodName) {
//        return isTargetMethod("batchDelete", methodName);
//    }
//
//    /**
//     * 费用批量删除方法
//     */
//    private boolean isBatchDeleteMethodToCost(String methodName) {
//        return isTargetMethod("batchDelete", methodName);
//    }
//
//    /**
//     * 还原方法
//     */
//    private boolean isRestoreMethod(String methodName) {
//        return isTargetMethod("restore", methodName);
//    }
//
//    /**
//     * 反结账方法
//     */
//    private boolean isAntiCheckoutMethod(String methodName) {
//        return isTargetMethod("antiCheckout", methodName);
//    }
//
//    /**
//     * 合同恢复方法
//     */
//    private boolean isRecoveryContractMethod(String methodName) {
//        return isTargetMethod("recoveryContract", methodName);
//    }
//    private boolean isTargetMethod(String name, String targetMethodName) {
//        return name.equalsIgnoreCase(targetMethodName);
//    }
//
//    private com.wukongxiaozi.api.model.OperateLog packageLogModel(EnterpriseUser currentUser, AccountSet accountSet,
//                                                                  Short type, String path, String content,
//                                                                  String ipAddress, Short scope) {
//        com.wukongxiaozi.api.model.OperateLog log = new com.wukongxiaozi.api.model.OperateLog();
//        log.setOperateTime(new Date());
//        if (currentUser != null) {
//            log.setOperateUserId(currentUser.getId());
//            log.setOperateUserName(currentUser.getName().length() > 5 ? currentUser.getName().substring(0, 5)
//                    : currentUser.getName());
//            log.setOperateUserUsername(currentUser.getUsername());
//            log.setEnterpriseId(currentUser.getEnterpriseId());
//        } else {
//            log.setOperateUserId(0L);
//            log.setOperateUserName("");
//            log.setOperateUserUsername("");
//            log.setEnterpriseId(0L);
//        }
//        log.setPath(path);
//        log.setType(type);
//        log.setContent(content);
//        log.setOperateIp(ipAddress);
//        log.setScope(scope);
//        if (accountSet != null) {
//            log.setAccountSetId(accountSet.getId());
//        } else {
//            log.setAccountSetId(0L);
//        }
//        return log;
//    }
//
//    @Autowired
//    public void setAuthenticationFacade(IAuthenticationFacade authenticationFacade) {
//        this.authenticationFacade = authenticationFacade;
//    }
//
//    @Autowired
//    public void setAccountSetHolder(AccountSetHolder accountSetHolder) {
//        this.accountSetHolder = accountSetHolder;
//    }
//
//    @Autowired
//    public void setTemplate(StringRedisTemplate template) {
//        this.template = template;
//    }
//
//    public static String getIpAddress(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
//}
