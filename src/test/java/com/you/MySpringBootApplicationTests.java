package com.you;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.you.bean.User;
import com.you.service.AESService;
import com.you.util.FastJsonUtil;
import com.you.util.RedisUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySpringBootApplicationTests {
    
    @Autowired
    private AESService aESService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FastJsonUtil fastJsonUtil;

	@Test
	public void contextLoads() {
//	    String string = "<?xml version='1.0' encoding='GBK'?>\r\n" + 
//	                    "<Package>\r\n" + 
//	                    "  <Header>\r\n" + 
//	                    "    <RequestType>MITNPCheck</RequestType>\r\n" + 
//	                    "    <UUID>6d72187462d24efa8768e8e9ce5ce890</UUID>\r\n" + 
//	                    "    <SendTime>2020-03-02 17:17:56</SendTime>\r\n" + 
//	                    "  </Header>\r\n" + 
//	                    "  <Request>\r\n" + 
//	                    "    <PolicyInfo>\r\n" + 
//	                    "      <PrtNo>YBT2020030200023</PrtNo>\r\n" + 
//	                    "      <DataType>2010203</DataType>\r\n" + 
//	                    "      <CheckSign>1</CheckSign>\r\n" + 
//	                    "      <CheckDate>2020-03-02</CheckDate>\r\n" + 
//	                    "      <AgentReport>\r\n" + 
//	                    "        <Approach>3</Approach>\r\n" + 
//	                    "        <Purpose>0</Purpose>\r\n" + 
//	                    "      </AgentReport>\r\n" + 
//	                    "      <AnnualIncome>500000</AnnualIncome>\r\n" + 
//	                    "      <SourceOfIncome>工资</SourceOfIncome>\r\n" + 
//	                    "      <TotalAssets>5000000</TotalAssets>\r\n" + 
//	                    "      <ThirdPtIdentity>0</ThirdPtIdentity>\r\n" + 
//	                    "      <PolApplyDate>2020-03-02</PolApplyDate>\r\n" + 
//	                    "      <ServAgentName>万妍伶</ServAgentName>\r\n" + 
//	                    "      <ServAgentCode>6101000247</ServAgentCode>\r\n" + 
//	                    "      <ServAgentRatio>100</ServAgentRatio>\r\n" + 
//	                    "      <IsReadItem>Y</IsReadItem>\r\n" + 
//	                    "      <IsExplain>Y</IsExplain>\r\n" + 
//	                    "      <LetterPostFlag>0</LetterPostFlag>\r\n" + 
//	                    "      <SubmitFlag>0</SubmitFlag>\r\n" + 
//	                    "      <AptFlag>5</AptFlag>\r\n" + 
//	                    "      <IsPaperPrint>N</IsPaperPrint>\r\n" + 
//	                    "    </PolicyInfo>\r\n" + 
//	                    "    <CustInfo>\r\n" + 
//	                    "      <Appnt>\r\n" + 
//	                    "        <AppntName>重庆一</AppntName>\r\n" + 
//	                    "        <AppntSex>0</AppntSex>\r\n" + 
//	                    "        <AppntBirthday>1990-03-07</AppntBirthday>\r\n" + 
//	                    "        <AppntStature>170.00</AppntStature>\r\n" + 
//	                    "        <AppntAvoirdupois>50.00</AppntAvoirdupois>\r\n" + 
//	                    "        <IDType>11</IDType>\r\n" + 
//	                    "        <IDNo>500101199003079078</IDNo>\r\n" + 
//	                    "        <IDStartDate>2013-01-01</IDStartDate>\r\n" + 
//	                    "        <IDExpDate>2023-01-01</IDExpDate>\r\n" + 
//	                    "        <Marriage>10</Marriage>\r\n" + 
//	                    "        <Province>500000</Province>\r\n" + 
//	                    "        <City>500100</City>\r\n" + 
//	                    "        <County>万州区</County>\r\n" + 
//	                    "        <Street>街道116</Street>\r\n" + 
//	                    "        <Community>社区116</Community>\r\n" + 
//	                    "        <PostalAddress>详细地址116</PostalAddress>\r\n" + 
//	                    "        <ZipCode>404100</ZipCode>\r\n" + 
//	                    "        <Mobile>13216666189</Mobile>\r\n" + 
//	                    "        <EMail>2317927@qq.com</EMail>\r\n" + 
//	                    "        <GrpName>工作单位116</GrpName>\r\n" + 
//	                    "        <AppOccupation>企事业单位外勤业务员</AppOccupation>\r\n" + 
//	                    "        <OccupationType>2</OccupationType>\r\n" + 
//	                    "        <OccupationCode>0001003</OccupationCode>\r\n" + 
//	                    "        <Salary>500000</Salary>\r\n" + 
//	                    "        <AppntCopy>1</AppntCopy>\r\n" + 
//	                    "        <AppntSign>1</AppntSign>\r\n" + 
//	                    "        <AppResFlag>1</AppResFlag>\r\n" + 
//	                    "        <AppntTaxInfo>\r\n" + 
//	                    "          <TaxIdentity>1</TaxIdentity>\r\n" + 
//	                    "          <TaxInfoList>\r\n" + 
//	                    "            <TaxInfo/>\r\n" + 
//	                    "          </TaxInfoList>\r\n" + 
//	                    "        </AppntTaxInfo>\r\n" + 
//	                    "        <AppntIsHasInsurance>1</AppntIsHasInsurance>\r\n" + 
//	                    "      </Appnt>\r\n" + 
//	                    "      <Insurants>\r\n" + 
//	                    "        <InsCount>1</InsCount>\r\n" + 
//	                    "        <Insurant>\r\n" + 
//	                    "          <InsSeq>1</InsSeq>\r\n" + 
//	                    "          <Name>重庆一</Name>\r\n" + 
//	                    "          <Sex>0</Sex>\r\n" + 
//	                    "          <Birthday>1990-03-07</Birthday>\r\n" + 
//	                    "          <Stature>170.00</Stature>\r\n" + 
//	                    "          <Avoirdupois>50.00</Avoirdupois>\r\n" + 
//	                    "          <IDType>11</IDType>\r\n" + 
//	                    "          <IDNo>500101199003079078</IDNo>\r\n" + 
//	                    "          <IDStartDate>2013-01-01</IDStartDate>\r\n" + 
//	                    "          <IDExpDate>2023-01-01</IDExpDate>\r\n" + 
//	                    "          <Marriage>10</Marriage>\r\n" + 
//	                    "          <Province>500000</Province>\r\n" + 
//	                    "          <City>500100</City>\r\n" + 
//	                    "          <County>万州区</County>\r\n" + 
//	                    "          <Street>街道116</Street>\r\n" + 
//	                    "          <Community>社区116</Community>\r\n" + 
//	                    "          <PostalAddress>详细地址116</PostalAddress>\r\n" + 
//	                    "          <ZipCode>404100</ZipCode>\r\n" + 
//	                    "          <Mobile>13216666189</Mobile>\r\n" + 
//	                    "          <EMail>2317927@qq.com</EMail>\r\n" + 
//	                    "          <GrpName>工作单位116</GrpName>\r\n" + 
//	                    "          <InsOccupation>企事业单位外勤业务员</InsOccupation>\r\n" + 
//	                    "          <OccupationType>2</OccupationType>\r\n" + 
//	                    "          <OccupationCode>0001003</OccupationCode>\r\n" + 
//	                    "          <RelToApp>00</RelToApp>\r\n" + 
//	                    "          <Salary>500000</Salary>\r\n" + 
//	                    "          <InsSign>1</InsSign>\r\n" + 
//	                    "          <InsResFlag>0</InsResFlag>\r\n" + 
//	                    "          <InsurantTaxInfo>\r\n" + 
//	                    "            <TaxInfoList>\r\n" + 
//	                    "              <TaxInfo/>\r\n" + 
//	                    "            </TaxInfoList>\r\n" + 
//	                    "          </InsurantTaxInfo>\r\n" + 
//	                    "          <IsHasInsurance>1</IsHasInsurance>\r\n" + 
//	                    "        </Insurant>\r\n" + 
//	                    "      </Insurants>\r\n" + 
//	                    "      <BnfInfo>\r\n" + 
//	                    "        <BnfType>0</BnfType>\r\n" + 
//	                    "        <beneficiaries/>\r\n" + 
//	                    "      </BnfInfo>\r\n" + 
//	                    "    </CustInfo>\r\n" + 
//	                    "    <ProductInfo>\r\n" + 
//	                    "      <NewPayMode>3</NewPayMode>\r\n" + 
//	                    "      <SumPremL>6341.00</SumPremL>\r\n" + 
//	                    "      <PayIntv>12</PayIntv>\r\n" + 
//	                    "      <BankCode>102</BankCode>\r\n" + 
//	                    "      <BankAccNo>6214920201111190</BankAccNo>\r\n" + 
//	                    "      <BankAccName>重庆一</BankAccName>\r\n" + 
//	                    "      <AutoPayFlag>0</AutoPayFlag>\r\n" + 
//	                    "      <Products>\r\n" + 
//	                    "        <ProdCount>1</ProdCount>\r\n" + 
//	                    "        <Product>\r\n" + 
//	                    "          <ProdInsSeq>1</ProdInsSeq>\r\n" + 
//	                    "          <ProdName>光大永明爱多多重大疾病保险（含可选）</ProdName>\r\n" + 
//	                    "          <ProdCode>HCT023</ProdCode>\r\n" + 
//	                    "          <PayYear>5</PayYear>\r\n" + 
//	                    "          <PayYearFlag>年</PayYearFlag>\r\n" + 
//	                    "          <InsureYear>保终身</InsureYear>\r\n" + 
//	                    "          <InsureYearFlag>年</InsureYearFlag>\r\n" + 
//	                    "          <Amnt>50000</Amnt>\r\n" + 
//	                    "          <Prem>6341.000000000</Prem>\r\n" + 
//	                    "        </Product>\r\n" + 
//	                    "      </Products>\r\n" + 
//	                    "    </ProductInfo>\r\n" + 
//	                    "    <Question>\r\n" + 
//	                    "      <ResQuestion/>\r\n" + 
//	                    "      <CQQuestion>\r\n" + 
//	                    "        <AppntName>重庆一</AppntName>\r\n" + 
//	                    "        <AppntSex>0</AppntSex>\r\n" + 
//	                    "        <AppntAge>29</AppntAge>\r\n" + 
//	                    "        <SocialInsu>Y</SocialInsu>\r\n" + 
//	                    "        <SocialInsuA>Y</SocialInsuA>\r\n" + 
//	                    "        <SocialInsuB>Y</SocialInsuB>\r\n" + 
//	                    "        <SocialInsuC>Y</SocialInsuC>\r\n" + 
//	                    "        <CommercialInsu>N</CommercialInsu>\r\n" + 
//	                    "        <CommercialInsuA>N</CommercialInsuA>\r\n" + 
//	                    "        <CommercialInsuB>N</CommercialInsuB>\r\n" + 
//	                    "        <CommercialInsuC>N</CommercialInsuC>\r\n" + 
//	                    "        <CommercialInsuD>N</CommercialInsuD>\r\n" + 
//	                    "        <DeathInsu>Y</DeathInsu>\r\n" + 
//	                    "        <DeathInsuValue>1000000</DeathInsuValue>\r\n" + 
//	                    "        <DeathInsuYear>50</DeathInsuYear>\r\n" + 
//	                    "        <MajorDiseaseInsu>Y</MajorDiseaseInsu>\r\n" + 
//	                    "        <MajorDiseaseInsuValue>1000000</MajorDiseaseInsuValue>\r\n" + 
//	                    "        <MajorDiseaseInsuYear>50</MajorDiseaseInsuYear>\r\n" + 
//	                    "        <MedicalInsu>N</MedicalInsu>\r\n" + 
//	                    "        <EducationInsu>N</EducationInsu>\r\n" + 
//	                    "        <SurvivalInsu>N</SurvivalInsu>\r\n" + 
//	                    "        <AnnuityInsu>N</AnnuityInsu>\r\n" + 
//	                    "        <FengHong>N</FengHong>\r\n" + 
//	                    "        <WanNeng>N</WanNeng>\r\n" + 
//	                    "        <TouLian>N</TouLian>\r\n" + 
//	                    "        <BuKanZhong>Y</BuKanZhong>\r\n" + 
//	                    "        <AllPayItem>Y</AllPayItem>\r\n" + 
//	                    "        <AllPayPrem>1000</AllPayPrem>\r\n" + 
//	                    "        <PeriodPayItem>N</PeriodPayItem>\r\n" + 
//	                    "        <RiskName>光大永明爱多多重大疾病保险（含可选）</RiskName>\r\n" + 
//	                    "        <InsuYears>终生</InsuYears>\r\n" + 
//	                    "        <Amnt>50000</Amnt>\r\n" + 
//	                    "        <PayIntv>年交</PayIntv>\r\n" + 
//	                    "        <Prem>6341</Prem>\r\n" + 
//	                    "        <PayYears1>5年</PayYears1>\r\n" + 
//	                    "        <AppSign>重庆一</AppSign>\r\n" + 
//	                    "        <AppSignDate>2020-03-02</AppSignDate>\r\n" + 
//	                    "        <AgentSign>万妍伶</AgentSign>\r\n" + 
//	                    "        <AgentCode>6101000247</AgentCode>\r\n" + 
//	                    "        <AgentCompany>单位</AgentCompany>\r\n" + 
//	                    "        <CheckSugItem>Y</CheckSugItem>\r\n" + 
//	                    "        <CheckReason>同意</CheckReason>\r\n" + 
//	                    "        <CheckSign>审核者</CheckSign>\r\n" + 
//	                    "        <CheckSignDate>2020-03-02</CheckSignDate>\r\n" + 
//	                    "      </CQQuestion>\r\n" + 
//	                    "    </Question>\r\n" + 
//	                    "    <DocList/>\r\n" + 
//	                    "  </Request>\r\n" + 
//	                    "</Package>";
	    // xml文件加密
//	    String encryptString = aESService.getEncrypt(string);
//	    System.out.println("加密文件："+encryptString);
//	    // 解密
//	    String decryptString = aESService.getDecrypt(encryptString);
//	    System.out.println("解密文件："+decryptString);
	    
	    List<User> list = new ArrayList<User>();
	    User user = new User();
	    user.setAppId("123");
	    user.setPassword("哈哈");
	    User user1 = new User();
	    user1.setAppId("456");
	    user1.setPassword("少游");
	    list.add(user);
	    list.add(user1);
	    
	    String str=UUID.randomUUID().toString();
	    String value=UUID.randomUUID().toString();
	    String key = "you";
//	    // 存储字符串
//        boolean save = redisUtil.set(key,fastJsonUtil.listToJson(list));
//        System.out.println("save--------------------->"+save);
//        // 存储哈希值
//        boolean f = redisUtil.hset("jiashaoyou1", "user", user);
//        System.out.println("f-------->"+f);
//        // 存储list集合
//        boolean m = redisUtil.lSet("jiashaoyou2", list);
//        System.out.println("m-------->"+m);
	    
//	    //获取过期时间
//	    long a = redisUtil.getExpire(key);
//	    System.out.println("a------->"+a);
//	    // 获取值
//	    String b = (String) redisUtil.get(key);
//	    System.out.println("b--------->"+b);
//	    List<User> listUser = fastJsonUtil.jsonToList(b,User.class);
//	    for(int i = 0; i < listUser.size(); i++) {
//          System.out.println("JSONObject------->"+listUser.get(i));
//          }
//	    // 判断key是否存在
//	    boolean c = redisUtil.hasKey(key);
//	    System.out.println("c-------->"+c);
//	    // 设置过期时间
//	    boolean d = redisUtil.expire(key, 300);
//	    System.out.println("d-------->"+d);
//	    // 所存储值的数据类型
//	    DataType ter = redisUtil.type(key);
//	    System.out.println("ter---------->"+ter);
//	    // 删除
//	    redisUtil.delete(key);
	}

}
