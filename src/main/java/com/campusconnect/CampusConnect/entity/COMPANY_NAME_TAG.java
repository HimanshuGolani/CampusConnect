package com.campusconnect.CampusConnect.entity;

import lombok.Getter;

@Getter
public enum COMPANY_NAME_TAG {
    TCS("Tata Consultancy Services"),
    INFOSYS("Infosys"),
    WIPRO("Wipro"),
    HCL("HCL Technologies"),
    TECH_MAHINDRA("Tech Mahindra"),
    ACCENTURE("Accenture"),
    CAPGEMINI("Capgemini"),
    LTI("LTIMindtree"), // Merged name for L&T Infotech and Mindtree
    MINDTREE("LTIMindtree"),
    COGNIZANT("Cognizant"),
    IBM("IBM"), // Standardized short form
    SAP("SAP SE"),
    DELL("Dell Technologies"),
    HP("HP Inc."), // Hewlett-Packard split into HP Inc. and HPE
    HPE("Hewlett Packard Enterprise"),
    EY("Ernst & Young (EY)"),
    EY_GDS("EY Global Delivery Services"),
    KPMG("KPMG"),
    DELOITTE("Deloitte"),
    PWC("PricewaterhouseCoopers (PwC)"),
    NIIT("NIIT Technologies"),
    NIQ("NielsenIQ"),
    EPAM("EPAM Systems"),
    ZENSAR("Zensar Technologies"),
    MPHASIS("Mphasis"),
    HEXAWARE("Hexaware Technologies"),
    L_AND_T("Larsen & Toubro"),
    TATA_ADVANCED_SYSTEMS("Tata Advanced Systems Limited"),
    SASKEN("Sasken Technologies"),
    QUANTUM("Quantum IT Services"),
    ZOHO("Zoho Corporation"),

    // Banking & Finance
    HDFC("HDFC Bank"),
    ICICI("ICICI Bank"),
    SBI("State Bank of India"), // Changed from STATE_BANK
    AXIS_BANK("Axis Bank"),
    BANK_OF_BARODA("Bank of Baroda"),
    KOTAK_MAHINDRA("Kotak Mahindra Bank"),

    // Conglomerates
    RELIANCE("Reliance Industries"),
    ADANI("Adani Group"),

    // Telecom
    BHARTI_AIRTEL("Bharti Airtel"),
    VODAFONE_IDEA("Vodafone Idea"),
    JIO("Reliance Jio"),

    // IT & Internet Services
    GOOGLE("Google LLC"),
    MICROSOFT("Microsoft Corporation"),
    AMAZON("Amazon.com, Inc."),
    APPLE("Apple Inc."),
    META("Meta Platforms"), // Changed from FACEBOOK
    TWITTER("X (formerly Twitter)"),
    SNAPCHAT("Snap Inc."),
    INSTAGRAM("Instagram"),
    LINKEDIN("LinkedIn Corporation"),
    SPOTIFY("Spotify Technology S.A."),
    SLACK("Slack Technologies"),
    DROPBOX("Dropbox, Inc."),
    ROBLOX("Roblox Corporation"),
    EPIC_GAMES("Epic Games, Inc."),

    // E-commerce & Fintech
    ZOMATO("Zomato"),
    SWIGGY("Swiggy"),
    PAYTM("Paytm (One97 Communications)"),
    PHONEPE("PhonePe");

    private final String fullName;

    COMPANY_NAME_TAG(String fullName) {
        this.fullName = fullName;
    }
}
