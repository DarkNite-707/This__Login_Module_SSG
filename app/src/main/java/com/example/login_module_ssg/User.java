package com.example.login_module_ssg;

public class User {
   public String U_Name ;
    public String U_Email ;


    public User (){}

    public User (String Usr_Name,String Usr_Email)
    {

        this.U_Name =Usr_Name;
        this.U_Email = Usr_Email;


    }

 /*   public User (String Usr_Email,String Usr_Password,String Usr_Name)
    {

        this.U_Name =Usr_Name;
        this.U_Password = Usr_Password;
        this.U_Email = Usr_Email;


    }*/


    public String getU_Name() {
        return U_Name;
    }

    public void setU_Name(String u_Name) {
        U_Name = u_Name;
    }

    /*public String getU_Password() {
        return U_Password;
    }

    public void setU_Password(String u_Password) {
        U_Password = u_Password;
    }
*/
    public String getU_Email() {
        return U_Email;
    }

    public void setU_Email(String u_Email) {
        U_Email = u_Email;
    }

}
