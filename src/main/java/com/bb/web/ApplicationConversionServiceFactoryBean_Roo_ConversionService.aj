// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.web;

import com.bb.domain.Card;
import com.bb.domain.Company;
import com.bb.domain.Customer;
import com.bb.domain.CustomerCard;
import com.bb.domain.CustomerCheckin;
import com.bb.domain.CustomerPayment;
import com.bb.domain.CustomerProduct;
import com.bb.domain.CustomerProfit;
import com.bb.domain.CustomerTransaction;
import com.bb.domain.Faq;
import com.bb.domain.Location;
import com.bb.domain.ProductCommit;
import com.bb.domain.ProductStake;
import com.bb.domain.ref.RefPaymentTxType;
import com.bb.domain.ref.RefPaymentType;
import com.bb.domain.ref.RefSex;
import com.bb.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Card, String> ApplicationConversionServiceFactoryBean.getCardToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.Card, java.lang.String>() {
            public String convert(Card card) {
                return new StringBuilder().append(card.getName()).append(" ").append(card.getMaxUsage()).append(" ").append(card.getStartDate()).append(" ").append(card.getEndDate()).toString();
            }
        };
    }
    
    public Converter<Long, Card> ApplicationConversionServiceFactoryBean.getIdToCardConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.Card>() {
            public com.bb.domain.Card convert(java.lang.Long id) {
                return Card.findCard(id);
            }
        };
    }
    
    public Converter<String, Card> ApplicationConversionServiceFactoryBean.getStringToCardConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.Card>() {
            public com.bb.domain.Card convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Card.class);
            }
        };
    }
    
    public Converter<Company, String> ApplicationConversionServiceFactoryBean.getCompanyToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.Company, java.lang.String>() {
            public String convert(Company company) {
                return new StringBuilder().append(company.getName()).toString();
            }
        };
    }
    
    public Converter<Long, Company> ApplicationConversionServiceFactoryBean.getIdToCompanyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.Company>() {
            public com.bb.domain.Company convert(java.lang.Long id) {
                return Company.findCompany(id);
            }
        };
    }
    
    public Converter<String, Company> ApplicationConversionServiceFactoryBean.getStringToCompanyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.Company>() {
            public com.bb.domain.Company convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Company.class);
            }
        };
    }
    
    public Converter<Long, Customer> ApplicationConversionServiceFactoryBean.getIdToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.Customer>() {
            public com.bb.domain.Customer convert(java.lang.Long id) {
                return Customer.findCustomer(id);
            }
        };
    }
    
    public Converter<String, Customer> ApplicationConversionServiceFactoryBean.getStringToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.Customer>() {
            public com.bb.domain.Customer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Customer.class);
            }
        };
    }
    
    public Converter<CustomerCard, String> ApplicationConversionServiceFactoryBean.getCustomerCardToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.CustomerCard, java.lang.String>() {
            public String convert(CustomerCard customerCard) {
                return new StringBuilder().append(customerCard.getUsedDate()).toString();
            }
        };
    }
    
    public Converter<Long, CustomerCard> ApplicationConversionServiceFactoryBean.getIdToCustomerCardConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.CustomerCard>() {
            public com.bb.domain.CustomerCard convert(java.lang.Long id) {
                return CustomerCard.findCustomerCard(id);
            }
        };
    }
    
    public Converter<String, CustomerCard> ApplicationConversionServiceFactoryBean.getStringToCustomerCardConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.CustomerCard>() {
            public com.bb.domain.CustomerCard convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CustomerCard.class);
            }
        };
    }
    
    public Converter<CustomerCheckin, String> ApplicationConversionServiceFactoryBean.getCustomerCheckinToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.CustomerCheckin, java.lang.String>() {
            public String convert(CustomerCheckin customerCheckin) {
                return new StringBuilder().append(customerCheckin.getStartDate()).append(" ").append(customerCheckin.getEndDate()).toString();
            }
        };
    }
    
    public Converter<Long, CustomerCheckin> ApplicationConversionServiceFactoryBean.getIdToCustomerCheckinConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.CustomerCheckin>() {
            public com.bb.domain.CustomerCheckin convert(java.lang.Long id) {
                return CustomerCheckin.findCustomerCheckin(id);
            }
        };
    }
    
    public Converter<String, CustomerCheckin> ApplicationConversionServiceFactoryBean.getStringToCustomerCheckinConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.CustomerCheckin>() {
            public com.bb.domain.CustomerCheckin convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CustomerCheckin.class);
            }
        };
    }
    
    public Converter<CustomerPayment, String> ApplicationConversionServiceFactoryBean.getCustomerPaymentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.CustomerPayment, java.lang.String>() {
            public String convert(CustomerPayment customerPayment) {
                return new StringBuilder().append(customerPayment.getAccountId()).append(" ").append(customerPayment.getStartDate()).append(" ").append(customerPayment.getEndDate()).toString();
            }
        };
    }
    
    public Converter<Long, CustomerPayment> ApplicationConversionServiceFactoryBean.getIdToCustomerPaymentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.CustomerPayment>() {
            public com.bb.domain.CustomerPayment convert(java.lang.Long id) {
                return CustomerPayment.findCustomerPayment(id);
            }
        };
    }
    
    public Converter<String, CustomerPayment> ApplicationConversionServiceFactoryBean.getStringToCustomerPaymentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.CustomerPayment>() {
            public com.bb.domain.CustomerPayment convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CustomerPayment.class);
            }
        };
    }
    
    public Converter<CustomerProduct, String> ApplicationConversionServiceFactoryBean.getCustomerProductToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.CustomerProduct, java.lang.String>() {
            public String convert(CustomerProduct customerProduct) {
                return new StringBuilder().append(customerProduct.getStartDate()).append(" ").append(customerProduct.getEndDate()).toString();
            }
        };
    }
    
    public Converter<Long, CustomerProduct> ApplicationConversionServiceFactoryBean.getIdToCustomerProductConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.CustomerProduct>() {
            public com.bb.domain.CustomerProduct convert(java.lang.Long id) {
                return CustomerProduct.findCustomerProduct(id);
            }
        };
    }
    
    public Converter<String, CustomerProduct> ApplicationConversionServiceFactoryBean.getStringToCustomerProductConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.CustomerProduct>() {
            public com.bb.domain.CustomerProduct convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CustomerProduct.class);
            }
        };
    }
    
    public Converter<CustomerProfit, String> ApplicationConversionServiceFactoryBean.getCustomerProfitToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.CustomerProfit, java.lang.String>() {
            public String convert(CustomerProfit customerProfit) {
                return new StringBuilder().append(customerProfit.getStartDate()).append(" ").append(customerProfit.getEndDate()).append(" ").append(customerProfit.getAmount()).toString();
            }
        };
    }
    
    public Converter<Long, CustomerProfit> ApplicationConversionServiceFactoryBean.getIdToCustomerProfitConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.CustomerProfit>() {
            public com.bb.domain.CustomerProfit convert(java.lang.Long id) {
                return CustomerProfit.findCustomerProfit(id);
            }
        };
    }
    
    public Converter<String, CustomerProfit> ApplicationConversionServiceFactoryBean.getStringToCustomerProfitConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.CustomerProfit>() {
            public com.bb.domain.CustomerProfit convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CustomerProfit.class);
            }
        };
    }
    
    public Converter<CustomerTransaction, String> ApplicationConversionServiceFactoryBean.getCustomerTransactionToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.CustomerTransaction, java.lang.String>() {
            public String convert(CustomerTransaction customerTransaction) {
                return new StringBuilder().append(customerTransaction.getAmount()).append(" ").append(customerTransaction.getTransactionDate()).append(" ").append(customerTransaction.getTransactionError()).toString();
            }
        };
    }
    
    public Converter<Long, CustomerTransaction> ApplicationConversionServiceFactoryBean.getIdToCustomerTransactionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.CustomerTransaction>() {
            public com.bb.domain.CustomerTransaction convert(java.lang.Long id) {
                return CustomerTransaction.findCustomerTransaction(id);
            }
        };
    }
    
    public Converter<String, CustomerTransaction> ApplicationConversionServiceFactoryBean.getStringToCustomerTransactionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.CustomerTransaction>() {
            public com.bb.domain.CustomerTransaction convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CustomerTransaction.class);
            }
        };
    }
    
    public Converter<Faq, String> ApplicationConversionServiceFactoryBean.getFaqToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.Faq, java.lang.String>() {
            public String convert(Faq faq) {
                return new StringBuilder().append(faq.getQuestion()).append(" ").append(faq.getAnswer()).append(" ").append(faq.getQuestionOrder()).toString();
            }
        };
    }
    
    public Converter<Long, Faq> ApplicationConversionServiceFactoryBean.getIdToFaqConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.Faq>() {
            public com.bb.domain.Faq convert(java.lang.Long id) {
                return Faq.findFaq(id);
            }
        };
    }
    
    public Converter<String, Faq> ApplicationConversionServiceFactoryBean.getStringToFaqConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.Faq>() {
            public com.bb.domain.Faq convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Faq.class);
            }
        };
    }
    
    public Converter<Location, String> ApplicationConversionServiceFactoryBean.getLocationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.Location, java.lang.String>() {
            public String convert(Location location) {
                return new StringBuilder().append(location.getName()).append(" ").append(location.getAddress()).append(" ").append(location.getCity()).append(" ").append(location.getGpsInfo()).toString();
            }
        };
    }
    
    public Converter<Long, Location> ApplicationConversionServiceFactoryBean.getIdToLocationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.Location>() {
            public com.bb.domain.Location convert(java.lang.Long id) {
                return Location.findLocation(id);
            }
        };
    }
    
    public Converter<String, Location> ApplicationConversionServiceFactoryBean.getStringToLocationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.Location>() {
            public com.bb.domain.Location convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Location.class);
            }
        };
    }
    
    public Converter<ProductCommit, String> ApplicationConversionServiceFactoryBean.getProductCommitToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.ProductCommit, java.lang.String>() {
            public String convert(ProductCommit productCommit) {
                return new StringBuilder().append(productCommit.getCommits()).toString();
            }
        };
    }
    
    public Converter<Long, ProductCommit> ApplicationConversionServiceFactoryBean.getIdToProductCommitConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.ProductCommit>() {
            public com.bb.domain.ProductCommit convert(java.lang.Long id) {
                return ProductCommit.findProductCommit(id);
            }
        };
    }
    
    public Converter<String, ProductCommit> ApplicationConversionServiceFactoryBean.getStringToProductCommitConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.ProductCommit>() {
            public com.bb.domain.ProductCommit convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), ProductCommit.class);
            }
        };
    }
    
    public Converter<ProductStake, String> ApplicationConversionServiceFactoryBean.getProductStakeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.ProductStake, java.lang.String>() {
            public String convert(ProductStake productStake) {
                return new StringBuilder().append(productStake.getStakes()).toString();
            }
        };
    }
    
    public Converter<Long, ProductStake> ApplicationConversionServiceFactoryBean.getIdToProductStakeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.ProductStake>() {
            public com.bb.domain.ProductStake convert(java.lang.Long id) {
                return ProductStake.findProductStake(id);
            }
        };
    }
    
    public Converter<String, ProductStake> ApplicationConversionServiceFactoryBean.getStringToProductStakeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.ProductStake>() {
            public com.bb.domain.ProductStake convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), ProductStake.class);
            }
        };
    }
    
    public Converter<RefPaymentTxType, String> ApplicationConversionServiceFactoryBean.getRefPaymentTxTypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.ref.RefPaymentTxType, java.lang.String>() {
            public String convert(RefPaymentTxType refPaymentTxType) {
                return new StringBuilder().append(refPaymentTxType.getName()).toString();
            }
        };
    }
    
    public Converter<Long, RefPaymentTxType> ApplicationConversionServiceFactoryBean.getIdToRefPaymentTxTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.ref.RefPaymentTxType>() {
            public com.bb.domain.ref.RefPaymentTxType convert(java.lang.Long id) {
                return RefPaymentTxType.findRefPaymentTxType(id);
            }
        };
    }
    
    public Converter<String, RefPaymentTxType> ApplicationConversionServiceFactoryBean.getStringToRefPaymentTxTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.ref.RefPaymentTxType>() {
            public com.bb.domain.ref.RefPaymentTxType convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), RefPaymentTxType.class);
            }
        };
    }
    
    public Converter<RefPaymentType, String> ApplicationConversionServiceFactoryBean.getRefPaymentTypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.ref.RefPaymentType, java.lang.String>() {
            public String convert(RefPaymentType refPaymentType) {
                return new StringBuilder().append(refPaymentType.getName()).toString();
            }
        };
    }
    
    public Converter<Long, RefPaymentType> ApplicationConversionServiceFactoryBean.getIdToRefPaymentTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.ref.RefPaymentType>() {
            public com.bb.domain.ref.RefPaymentType convert(java.lang.Long id) {
                return RefPaymentType.findRefPaymentType(id);
            }
        };
    }
    
    public Converter<String, RefPaymentType> ApplicationConversionServiceFactoryBean.getStringToRefPaymentTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.ref.RefPaymentType>() {
            public com.bb.domain.ref.RefPaymentType convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), RefPaymentType.class);
            }
        };
    }
    
    public Converter<RefSex, String> ApplicationConversionServiceFactoryBean.getRefSexToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.bb.domain.ref.RefSex, java.lang.String>() {
            public String convert(RefSex refSex) {
                return new StringBuilder().append(refSex.getName()).toString();
            }
        };
    }
    
    public Converter<Long, RefSex> ApplicationConversionServiceFactoryBean.getIdToRefSexConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.bb.domain.ref.RefSex>() {
            public com.bb.domain.ref.RefSex convert(java.lang.Long id) {
                return RefSex.findRefSex(id);
            }
        };
    }
    
    public Converter<String, RefSex> ApplicationConversionServiceFactoryBean.getStringToRefSexConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.bb.domain.ref.RefSex>() {
            public com.bb.domain.ref.RefSex convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), RefSex.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getCardToStringConverter());
        registry.addConverter(getIdToCardConverter());
        registry.addConverter(getStringToCardConverter());
        registry.addConverter(getCompanyToStringConverter());
        registry.addConverter(getIdToCompanyConverter());
        registry.addConverter(getStringToCompanyConverter());
        registry.addConverter(getCustomerToStringConverter());
        registry.addConverter(getIdToCustomerConverter());
        registry.addConverter(getStringToCustomerConverter());
        registry.addConverter(getCustomerCardToStringConverter());
        registry.addConverter(getIdToCustomerCardConverter());
        registry.addConverter(getStringToCustomerCardConverter());
        registry.addConverter(getCustomerCheckinToStringConverter());
        registry.addConverter(getIdToCustomerCheckinConverter());
        registry.addConverter(getStringToCustomerCheckinConverter());
        registry.addConverter(getCustomerPaymentToStringConverter());
        registry.addConverter(getIdToCustomerPaymentConverter());
        registry.addConverter(getStringToCustomerPaymentConverter());
        registry.addConverter(getCustomerProductToStringConverter());
        registry.addConverter(getIdToCustomerProductConverter());
        registry.addConverter(getStringToCustomerProductConverter());
        registry.addConverter(getCustomerProfitToStringConverter());
        registry.addConverter(getIdToCustomerProfitConverter());
        registry.addConverter(getStringToCustomerProfitConverter());
        registry.addConverter(getCustomerTransactionToStringConverter());
        registry.addConverter(getIdToCustomerTransactionConverter());
        registry.addConverter(getStringToCustomerTransactionConverter());
        registry.addConverter(getFaqToStringConverter());
        registry.addConverter(getIdToFaqConverter());
        registry.addConverter(getStringToFaqConverter());
        registry.addConverter(getLocationToStringConverter());
        registry.addConverter(getIdToLocationConverter());
        registry.addConverter(getStringToLocationConverter());
        registry.addConverter(getProductCommitToStringConverter());
        registry.addConverter(getIdToProductCommitConverter());
        registry.addConverter(getStringToProductCommitConverter());
        registry.addConverter(getProductStakeToStringConverter());
        registry.addConverter(getIdToProductStakeConverter());
        registry.addConverter(getStringToProductStakeConverter());
        registry.addConverter(getRefPaymentTxTypeToStringConverter());
        registry.addConverter(getIdToRefPaymentTxTypeConverter());
        registry.addConverter(getStringToRefPaymentTxTypeConverter());
        registry.addConverter(getRefPaymentTypeToStringConverter());
        registry.addConverter(getIdToRefPaymentTypeConverter());
        registry.addConverter(getStringToRefPaymentTypeConverter());
        registry.addConverter(getRefSexToStringConverter());
        registry.addConverter(getIdToRefSexConverter());
        registry.addConverter(getStringToRefSexConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
