package com.techweb.onlinetest.admin.util;

public class URLConstants {

  public final static String AUTHORIZATION_URL = "auth";

  public final static String SAVE_MACRO_TREND = "admin/macrotrend";
  public final static String GET_MACRO_TREND = "admin/macrotrend";
  public final static String SAVE_IMPLICATION = "admin/macrotrend/%d/trendimplication";

  public final static String SAVE_INDUSTRY = "admin/industryheader";
  public final static String SAVE_ARCHETYPE = "admin/industryheader/%d/archetypeheader";
  public final static String SAVE_PAIN_POINTS = "admin/industryheader/%d/painpoint";
  public final static String SAVE_SEGMENTS = "admin/industryheader/%d/segment";
  public final static String SAVE_SUB_SEGMENTS = "admin/industryheader/%d/subsegment";
  public final static String SAVE_SEGMENT_BUSINESS_FLOW = "admin/industryheader/%d/sbf";
  public final static String GET_INDUSTRY_NAMES = SAVE_INDUSTRY;
  public final static String SAVE_VALUE_CHAIN_COMPONENT =
      "admin/industryheader/valuechaincomponents";
  public final static String GET_INDUSTRY_BY_NAME = "admin/industryheader/%s";
  public final static String SAVE_INDUSTRY_DOCUMENT = "admin/industryheader/document";

  public final static String UPDATE_INDUSTRY = "admin/industryheader/%d";
  public final static String UPDATE_INDUSTRY_FINANCIAL = "admin/industryheader/%s/financials";
  public final static String UPDATE_SEGMENT_FINANCIAL =
      "admin/industryheader/%s/segment/financials";
  public final static String UPDATE_INDUSTRY_SEGMENT = "admin/industryheader/%s/industrysegment";
  public final static String UPDATE_INDUSTRY_SEGMENT_MATRIX = "admin/industryheader/sfm";

  // Client
  public final static String GET_CLIENT_PROFILE_PARAMS = "/clientinsight/params/Profile";
  public final static String GET_YEARS_NAME = "/clientinsight/years";
  public final static String GET_CLIENT_FINANCIAL_PARAMS = "/clientinsight/params/Financials";
  public final static String GET_CLIENT_PARAMS = "/admin/client-insight/params";
  public final static String UPDATE_CLIENT_SAVE_UPDATE = "/admin/client-insight/clients";
  public final static String GET_CLIENT_BY_INDUSTRY = "/admin/client-insight/industry/%d";
  public final static String UPLOAD_CLIENT_LOGO = "/admin/client-insight/logo";
  public final static String CLIENT_SHAREHOLDING_IMPORT =
      "/admin/client-insight/shareholding/import";

  public final static String CLIENT_BUSINESS_FLOW_IMPORT =
      "/admin/client-insight/business-flow/insert";
  public final static String CLIENT_BUSINESS_FLOW_DETAIL_IMPORT =
      "/admin/client-insight/business-flow/detail/insert";
  public final static String CLIENT_BUSINESS_FLOW_UPDATE_TEXT =
      "/admin/client-insight/business-flow/text/update";

  public final static String CLIENT_ASSOCIATED_IMPORT = "/admin/client-insight/associated/import";
  public final static String CLIENT_CONNECTION_IMPORT = "/admin/client-insight/value-chain/import";
  public final static String CLIENT_FINANCIAL_IMPORT = "/admin/client-insight/financial/import";
  public final static String CLIENT_WALLET = "/admin/client-insight/wallet";
  public final static String CLIENT_PROFILE_DOWNLOAD = "/admin/client-insight/profile/industry/%d";

  // Master Note Admin
  public final static String MASTER_NOTES_GET_DATA_LIST =
      "/masternotes/detail/master-notes/get-all";
  public final static String MASTER_NOTES_GET_ON_MODULE_SUB_MODULE_AND_TYPE =
      "/masternotes/%s/%s/%s";
  public final static String SAVE_MASTER_NOTES_DETAILS = "/masternotes/detail/save";

  // pricing APIs
  public final static String PRICING_BCM_PRICING = "/admin/pricing/bcm";
  public final static String PRICING_BCM_PACKAGES = "/admin/pricing/bcmpackage";
  public final static String PRICING_CURRENCY = "/admin/pricing/currency";
  public final static String PRICING_LGD = "/admin/pricing/lgd";
  public final static String PRICING_EAD = "/admin/pricing/ead";
  public final static String PRICING_OPEX = "/admin/pricing/opex";
  public final static String PRICING_EXPENSE = "/admin/pricing/expenses";
  public final static String PRICING_PECM = "/admin/pricing/pecm";
  public final static String PRICING_EC = "/admin/pricing/ec";
  public final static String PRICING_OPS_RISK = "/admin/pricing/opsrisk";
  public final static String PRICING_FTP = "/admin/pricing/ftp";
  public final static String PRICING_PD = "/admin/pricing/pd";
  public final static String PRICING_ASSUMPTION = "/admin/pricing/assumptions";

  // product APIs
  public final static String PRODUCT = "/admin/product";
  public final static String PROUCT_PROGRAMS = "/admin/product/program";
  public final static String PROUCT_COMPETITION_BENCHMARK = "/admin/product/cbm";
  public final static String PROUCT_THRESHOLD = "/admin/product/threshold";
  public final static String PROUCT_OVERVIEW = "/admin/product/overview";
  public final static String PROUCT_PRICING_GET_PRODUCT = "product-pricing/products/level/%d";

  // talkingpoints
  public final static String MASTER_NOTES_DETAILS =
      "masternotes/detail/master-notes-details/get-all/%d/%d";
  public final static String DELETE_MASTER_NOTES_DETAILS =
      "masternotes/detail/master-notes-details/delete";

  public final static String GET_DOCUMENT_TYPES = "/on-boarding/get-doc-list";
  public final static String GET_PRODUCT_MASTERBYID = "/on-boarding/%d";
  public final static String GET_PRODUCT_MASTERBYIDANDDOCUMENTNAMEID = "/on-boarding/%d/%d";
  public final static String GET_PRODUCT_PRICINT_ON_LEVEL1 = "/product-pricing/products/level/1";
  public final static String SAVE_PRODUCT_MASTER = "on-boarding/detail/document-master/save";
  public final static String SAVE_PRODUCT_DOCUMENT = "on-boarding/detail/product-document/save";
  public final static String DELETE_PRODUCT_MASTER = "on-boarding/detail/document-master/delete";

  public static final String SHEET_HISTORY = "admin/uploadhistory";
  public static final String SHEET_GET_HISTORY_BY_ID = "admin/uploadhistory/%d";

  public final static String SAVE_INDUSTRY_BUSINESS_FLOW = "admin/industryheader/businessFlow";
  
  
}
