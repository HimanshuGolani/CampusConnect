package com.campusconnect.CampusConnect.entity;

import lombok.Getter;

@Getter
public enum COMPANY_NAME_TAG {
    TCS("Tata Consultancy Services"),
    INFOSYS("Infosys Limited"),
    WIPRO("Wipro Limited"),
    HCL("HCL Technologies"),
    TECH_MAHINDRA("Tech Mahindra"),
    ACCENTURE("Accenture"),
    CAPGEMINI("Capgemini"),
    LTI("Larsen & Toubro Infotech"),
    MINDTREE("Mindtree Limited"),
    COGNIZANT("Cognizant Technology Solutions"),
    IBM("International Business Machines"),
    SAP("SAP SE"),
    DELL("Dell Technologies"),
    HP("Hewlett-Packard"),
    EY("Ernst & Young"),
    EY_GDS("Ernst & Young Global Delivery Services"),
    KPMG("KPMG International"),
    DELOITTE("Deloitte Touche Tohmatsu Limited"),
    PWC("PricewaterhouseCoopers"),
    NIIT("NIIT Limited"),
    NIQ("NielsenIQ"),
    EPAM("EPAM Systems"),
    ZENSAR("Zensar Technologies"),
    MPHASIS("Mphasis Limited"),
    HEXAWARE("Hexaware Technologies"),
    L_AND_T("Larsen & Toubro Limited"),
    TATA_ADVANCED_SYSTEMS("Tata Advanced Systems"),
    SASKEN("Sasken Communication Technologies"),
    QUANTUM("Quantum IT Services"),
    ZOHO("Zoho Corporation"),
    HDFC("Housing Development Finance Corporation"),
    ICICI("ICICI Bank"),
    STATE_BANK("State Bank of India"),
    AXIS_BANK("Axis Bank"),
    BANK_OF_BARODA("Bank of Baroda"),
    KOTAK_MAHINDRA("Kotak Mahindra Bank"),
    RELIANCE("Reliance Industries Limited"),
    ADANI("Adani Group"),
    BHARTI_AIRTEL("Bharti Airtel"),
    VODAFONE_IDEA("Vodafone Idea Limited"),
    JIO("Jio (Reliance Jio Infocomm Limited)"),
    TATA_CONSULTANCY_SERVICES("Tata Consultancy Services"),
    ZOMATO("Zomato Limited"),
    SWIGGY("Swiggy"),
    PAYTM("Paytm"),
    PHONEPE("PhonePe"),
    GOOGLE("Google"),
    MICROSOFT("Microsoft"),
    AMAZON("Amazon"),
    APPLE("Apple"),
    FACEBOOK("Meta Platforms"),
    TWITTER("Twitter"),
    SNAPCHAT("Snapchat"),
    INSTAGRAM("Instagram"),
    LINKEDIN("LinkedIn"),
    SPOTIFY("Spotify"),
    SLACK("Slack Technologies"),
    DROPBOX("Dropbox"),
    ROBLOX("Roblox Corporation"),
    EPIC_GAMES("Epic Games");

    private final String fullName;

    COMPANY_NAME_TAG(String fullName) {
        this.fullName = fullName;
    }

}

    
/* The form form which you can access the specific data.*/ 


// String fullName = COMPANY_NAME_TAG.EY.getFullName();
// System.out.println(fullName); // Output: Ernst & Young

/* 
public class EnumExample {
    public static void main(String[] args) {
        // Access the tag as a constant
        COMPANY_NAME_TAG companyTag = COMPANY_NAME_TAG.EY;
        System.out.println("Tag: " + companyTag); // Output: EY

        // Access the full name using the method
        String fullName = companyTag.getFullName();
        System.out.println("Full Name: " + fullName); // Output: Ernst & Young
    }
}
 */
