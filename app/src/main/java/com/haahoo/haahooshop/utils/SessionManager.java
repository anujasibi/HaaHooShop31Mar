package com.haahoo.haahooshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class SessionManager {

    private SharedPreferences sharedPreferences;


    public boolean card;

    public ArrayList<String>arrayList=new ArrayList<>();

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean isCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public SessionManager(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setTokens(String token){
        sharedPreferences.edit().putString("token",token).commit();
    }
    public String getTokens(){

        return  sharedPreferences.getString("token","");
    }
    public void setPins(String pins){
        sharedPreferences.edit().putString("pins",pins).commit();
    }
    public String getPins(){

        return  sharedPreferences.getString("pins","");
    }
    public void setSto(String sto){
        sharedPreferences.edit().putString("sto",sto).commit();
    }
    public String getSto(){

        return  sharedPreferences.getString("sto","");
    }


    public void setPdtName(String PdtName){
        sharedPreferences.edit().putString("PdtName",PdtName).commit();
    }
    public String getPdtName(){

        return  sharedPreferences.getString("PdtName","");
    }

    public void setempid(String empid){
        sharedPreferences.edit().putString("empid",empid).commit();
    }
    public String getempid(){

        return  sharedPreferences.getString("empid","");
    }

    public void setempbranch(String empbranch){
        sharedPreferences.edit().putString("empbranch",empbranch).commit();
    }
    public String getempbranch(){

        return  sharedPreferences.getString("empbranch","");
    }



    public void setdelvry_date(String delvry_date ){
        sharedPreferences.edit().putString("delvry_date ",delvry_date ).commit();
    }
    public String getdelvry_date(){

        return  sharedPreferences.getString("delvry_date ","");
    }
    public void setdelvry_tym(String delvry_tym ){
        sharedPreferences.edit().putString("delvry_tym ",delvry_tym ).commit();
    }
    public String getdelvry_tym(){

        return  sharedPreferences.getString("delvry_tym ","");
    }

    public void setphone(String phone){
        sharedPreferences.edit().putString("phone",phone).commit();
    }
    public String getphone(){

        return  sharedPreferences.getString("phone","");
    }
    public void setcancel_tym(String cancel_tym){
        sharedPreferences.edit().putString("cancel_tym",cancel_tym).commit();
    }
    public String getcancel_tym(){

        return  sharedPreferences.getString("cancel_tym","");
    }
    public void setpick_tym(String pick_tym){
        sharedPreferences.edit().putString("pick_tym",pick_tym).commit();
    }
    public String getpick_tym(){

        return  sharedPreferences.getString("pick_tym","");
    }
    public void setPdtaddvar(String pdtaddvar){
        sharedPreferences.edit().putString("pdtaddvar",pdtaddvar).commit();
    }
    public String getPdtaddvar(){

        return  sharedPreferences.getString("pdtaddvar","");
    }
    public void setvar(String var){
        sharedPreferences.edit().putString("var",var).commit();
    }
    public String getvar(){

        return  sharedPreferences.getString("var","");
    }
    public void setincent(String incent){
        sharedPreferences.edit().putString("incent",incent).commit();
    }
    public String getincent(){

        return  sharedPreferences.getString("incent","");
    }
    public void setresetphn(String rphn){
        sharedPreferences.edit().putString("rphn",rphn).commit();
    }
    public String getresetphn(){

        return  sharedPreferences.getString("rphn","");
    }
    public void setaddshop(String addshop){
        sharedPreferences.edit().putString("addshop",addshop).commit();
    }
    public String getaddshop(){

        return  sharedPreferences.getString("addshop","");
    }
    public void setreselprice(String resl){
        sharedPreferences.edit().putString("resl",resl).commit();
    }
    public String getreselprice(){

        return  sharedPreferences.getString("resl","");
    }

    public void setPdtid(String Pdtid){
        sharedPreferences.edit().putString("Pdtid",Pdtid).commit();
    }
    public String getPdtid(){

        return  sharedPreferences.getString("Pdtid","");
    }

    public void setPid(String Pid){
        sharedPreferences.edit().putString("Pid",Pid).commit();
    }
    public String getPid(){

        return  sharedPreferences.getString("Pid","");
    }


    public void setprice(String price){
        sharedPreferences.edit().putString("price",price).commit();
    }
    public String getprice(){

        return  sharedPreferences.getString("price","");
    }
    public void settype(String type){
        sharedPreferences.edit().putString("type",type).commit();
    }
    public String gettype(){

        return  sharedPreferences.getString("type","");
    }
    public void setret(String ret){
        sharedPreferences.edit().putString("ret",ret).commit();
    }
    public String getret(){

        return  sharedPreferences.getString("ret","");
    }
    public void setdis(String dis){
        sharedPreferences.edit().putString("dis",dis).commit();
    }
    public String getdis(){

        return  sharedPreferences.getString("dis","");
    }
    public void setstock(String stock){
        sharedPreferences.edit().putString("stock",stock).commit();
    }
    public String getstock(){

        return  sharedPreferences.getString("stock","");
    }
    public void setdes(String des){
        sharedPreferences.edit().putString("des",des).commit();
    }
    public String getdes(){

        return  sharedPreferences.getString("des","");
    }
    public void setcatName(String cname){
        sharedPreferences.edit().putString("cname",cname).commit();
    }
    public String getcatName(){

        return  sharedPreferences.getString("cname","");
    }
    public void setcatid(String cid){
        sharedPreferences.edit().putString("cid",cid).commit();
    }
    public String getcatid(){

        return  sharedPreferences.getString("cid","");
    }

    public String getcatdistance(){

        return  sharedPreferences.getString("distance","");
    }
    public void setcatdistance(String distance){
        sharedPreferences.edit().putString("distance",distance).commit();
    }

    /*public String getcheck(){

        return  sharedPreferences.getString("check","");
    }
    public void setcheck(String[] check){
        sharedPreferences.edit().putString("check",check).commit();
    }*/
    public String getsearch(){

        return  sharedPreferences.getString("search","");
    }
    public void setsearch(String search){
        sharedPreferences.edit().putString("search",search).commit();
    }
    public String getcheckn(){

        return  sharedPreferences.getString("checkn","");
    }
    public void setcheckn(String checkn){
        sharedPreferences.edit().putString("checkn",checkn).commit();
    }
    public String getdels(){

        return  sharedPreferences.getString("dels","");
    }
    public void setdels(String dels){
        sharedPreferences.edit().putString("dels",dels).commit();
    }
    public String getdelamount(){

        return  sharedPreferences.getString("delamount","");
    }
    public void setdelamount(String delamount){
        sharedPreferences.edit().putString("delamount",delamount).commit();
    }
    public String getradio(){

        return  sharedPreferences.getString("radio","");
    }
    public void setradio(String radio){
        sharedPreferences.edit().putString("radio",radio).commit();
    }

    public String getdisplay(){

        return  sharedPreferences.getString("display","");
    }
    public void setdisplay(String display){
        sharedPreferences.edit().putString("display",display).commit();
    }
    public String getmemory(){

        return  sharedPreferences.getString("memory","");
    }
    public void setmemory(String memory){
        sharedPreferences.edit().putString("memory",memory).commit();
    }


    public String getID() {
        String lat = sharedPreferences.getString("ID","");

        return lat;
    }

    public void setID(String ID) {
        sharedPreferences.edit().putString("ID",ID).commit();
    }
    public String getsub(){

        return  sharedPreferences.getString("sub","");
    }
    public void setsub(String sub){
        sharedPreferences.edit().putString("sub",sub).commit();
    }
    public String getsubvalue(){

        return  sharedPreferences.getString("subv","");
    }
    public void setsubvalue(String subv){
        sharedPreferences.edit().putString("subv",subv).commit();
    }
    public String getpayid(){

        return  sharedPreferences.getString("payid","");
    }
    public void setpayid(String payid){
        sharedPreferences.edit().putString("payid",payid).commit();
    }

    public String getcat(){

        return  sharedPreferences.getString("cattt","");
    }
    public void setcat(String cattt){
        sharedPreferences.edit().putString("cattt",cattt).commit();
    }

    public String getsubben(){

        return  sharedPreferences.getString("ben","");
    }
    public void setsubben(String ben){
        sharedPreferences.edit().putString("ben",ben).commit();
    }

    public String getshopname(){

        return  sharedPreferences.getString("sna","");
    }
    public void setshopname(String sna){
        sharedPreferences.edit().putString("sna",sna).commit();
    }
    public String getown(){

        return  sharedPreferences.getString("own","");
    }
    public void setown(String own){
        sharedPreferences.edit().putString("own",own).commit();
    }
    public String getgst(){

        return  sharedPreferences.getString("gst","");
    }
    public void setgst(String gst){
        sharedPreferences.edit().putString("gst",gst).commit();
    }
    public String getadd(){

        return  sharedPreferences.getString("add","");
    }
    public void setadd(String add){
        sharedPreferences.edit().putString("add",add).commit();
    }
    public String getdisid(){

        return  sharedPreferences.getString("disid","");
    }
    public void setdisid(String disid){
        sharedPreferences.edit().putString("disid",disid).commit();
    }
    public String getlat(){

        return  sharedPreferences.getString("lat","");
    }
    public void setlat(String lat){
        sharedPreferences.edit().putString("lat",lat).commit();
    }
    public String getlog(){

        return  sharedPreferences.getString("log","");
    }
    public void setlog(String log){
        sharedPreferences.edit().putString("log",log).commit();
    }
    public String getshopid(){

        return  sharedPreferences.getString("shopid","");
    }
    public void setshopid(String shopid){
        sharedPreferences.edit().putString("shopid",shopid).commit();
    }
    public String getrole(){

        return  sharedPreferences.getString("role","");
    }
    public void setrole(String role){
        sharedPreferences.edit().putString("role",role).commit();
    }
    public String getcatrid(){

        return  sharedPreferences.getString("rid","");
    }
    public void setcatrid(String rid){
        sharedPreferences.edit().putString("rid",rid).commit();
    }

    public String getpincode(){

        return  sharedPreferences.getString("pincode","");
    }
    public void setpincode(String pincode){
        sharedPreferences.edit().putString("pincode",pincode).commit();
    }

    public String getupiid(){

        return  sharedPreferences.getString("upiid","");
    }
    public void setupiid(String upiid){
        sharedPreferences.edit().putString("upiid",upiid).commit();
    }
    public String getdistrict(){

        return  sharedPreferences.getString("district","");
    }
    public void setdistrict(String district){
        sharedPreferences.edit().putString("district",district).commit();
    }

    public String getaddres(){

        return  sharedPreferences.getString("addres","");
    }
    public void setaddres(String addres){
        sharedPreferences.edit().putString("addres",addres).commit();
    }




}