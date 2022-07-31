package com.example.bus_ticketapplication.MODE;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class STKPush {
        @SerializedName("BusinessShortCode")
        private String businessShortCode;
        @SerializedName("Password")
        private String password;
        @SerializedName("Timestamp")
        private String timestamp;
        @SerializedName("TransactionType")
        private String transactionType;
        @SerializedName("Amount")
        private String amount;
        @SerializedName("PartyA")
        private String partyA;
        @SerializedName("PartyB")
        private String partyB;
        @SerializedName("PhoneNumber")
        private String phoneNumber;
        @SerializedName("CallBackURL")
        private String callBackURL;
        @SerializedName("AccountReference")
        private String accountReference;
        @SerializedName("TransactionDesc")
        private String transactionDesc;

        public STKPush(String businessShortCode, String password, String timestamp, String transactionType,
                       String amount, String partyA, String partyB, String phoneNumber, String callBackURL,
                       String accountReference, String transactionDesc) {
            this.businessShortCode = businessShortCode;
            this.password = password;
            this.timestamp = timestamp;
            this.transactionType = transactionType;
            this.amount = amount;
            this.partyA = partyA;
            this.partyB = partyB;
            this.phoneNumber = phoneNumber;
            this.callBackURL = callBackURL;
            this.accountReference = accountReference;
            this.transactionDesc = transactionDesc;
        }
        public String getBusinessShortCode() {
            return businessShortCode;
        }

        public void setBusinessShortCode(String businessShortCode) {
            this.businessShortCode = businessShortCode;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPartyA() {
            return partyA;
        }

        public void setPartyA(String partyA) {
            this.partyA = partyA;
        }

        public String getPartyB() {
            return partyB;
        }

        public void setPartyB(String partyB) {
            this.partyB = partyB;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getCallBackURL() {
            return callBackURL;
        }

        public void setCallBackURL(String callBackURL) {
            this.callBackURL = callBackURL;
        }

        public String getAccountReference() {
            return accountReference;
        }

        public void setAccountReference(String accountReference) {
            this.accountReference = accountReference;
        }

        public String getTransactionDesc() {
            return transactionDesc;
        }

        public void setTransactionDesc(String transactionDesc) {
            this.transactionDesc = transactionDesc;
        }

        public String toString() {
            return "{\"BusinessShortCode\":\"" + businessShortCode + "\"," +
                    "\"Password\":\"" + password + "\"," +
                    "\"Timestamp\":\"" + timestamp + "\"," +
                    "\"TransactionType\":\"" + transactionType + "\"," +
                    "\"Amount\":\"" + amount + "\"," +
                    "\"PartyA\":\"" + partyA + "\"," +
                    "\"PartyB\":\"" + partyB + "\"," +
                    "\"PhoneNumber\":\"" + phoneNumber + "\"," +
                    "\"CallBackURL\":\"" + callBackURL + "\"," +
                    "\"AccountReference\":\"" + accountReference + "\"," +
                    "\"TransactionDesc\":\"" + transactionDesc +
                    "\"}";
        }

        public String toJson(STKPush stkPush) {
            Gson gson = new Gson();
            return gson.toJson(stkPush);
        }

        public static String sanitizePhoneNumber(String phone) {

            if (phone.equals("")) {
                return "";
            }

            if (phone.length() < 11 & phone.startsWith("0")) {
                String p = phone.replaceFirst("^0", "254");
                return p;
            }
            if (phone.length() == 13 && phone.startsWith("+")) {
                String p = phone.replaceFirst("^+", "");
                return p;
            }
            return phone;
        }

        public static String getPassword(String businessShortCode, String passkey, String timestamp) {
            String str = businessShortCode + passkey + timestamp;
            //encode the password to Base64
            return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
        }

        public static String getTimestamp() {
            return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        }

    }




