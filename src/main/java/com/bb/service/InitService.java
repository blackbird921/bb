package com.bb.service;

import com.bb.domain.ref.RefPaymentType;
import com.bb.domain.ref.RefSex;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Calendar;

import static com.bb.util.StringHelper.parseUtf8String;

@Service
public class InitService {
    @PostConstruct
    public void init() {

//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信信信" ) ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信用" ) ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信信卡", "utf-8", "iso-8859-1") ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信信卡", "utf-8", "utf-16") ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信信卡", "utf-16", "iso-8859-1" ) ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信信卡", "windows-1252", "iso-8859-1" ) ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信信卡", "windows-1252", "utf-8" ) ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信信卡", "windows-1252", "utf-16" ) ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "信信卡", "windows-1252", "gbk" ) ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "卡用信" ) ).build().persist();
//        RefPaymentType.Builder.refPaymentType().withName( parseUtf8String( "支付宝" ) ).build().persist();
//
//        RefSex.RefSexBuilder.refSex().withName( parseUtf8String( "男" ) ).build().persist();
//        RefSex.RefSexBuilder.refSex().withName( parseUtf8String( "女" ) ).build().persist();
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.ceiling(Calendar.getInstance(), Calendar.MONDAY).getTime());

    }
}
