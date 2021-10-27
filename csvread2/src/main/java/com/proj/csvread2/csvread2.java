package com.proj.csvread2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Tariq
 */
public class csvread2 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
	public static void main(String[] args) throws IOException {

            
           
            String filename = System.console().readLine("Enter File Name: ");
            //String filename = (args[0]);
            File file = new File(filename);
            String deli = System.console().readLine("Enter Delimiter Type: ");
            
            System.out.println("File saved as output.txt");
            
            
          
		BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedReader r = new BufferedReader(new FileReader(file));

		String line = null;
                String line2 = null;
		Scanner scanner = null;
                Scanner s = null;
                String[] bufer;
		int index = 0;
                String word = "Omani";
                String word2 = "Italian";
                int wordCount = 0;
                int wordCount2 = 0;
		List<Data> dataList = new ArrayList<>();
                
 
                PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
                PrintStream out2 = new PrintStream(new FileOutputStream("output2.txt"));

		while (null != (line = reader.readLine())) {
			Data stat = new Data();
			scanner = new Scanner(line);
			scanner.useDelimiter(deli);
			while (scanner.hasNext()) {
				String data = scanner.next();
                            switch (index) {
                                case 0:
                                    stat.setFN(data);
                                    break;
                                case 1:
                                    stat.setLN(data);
                                    break;
                                case 2:
                                    stat.setAN(data);
                                    break;
                                case 3:
                                    stat.setDOB(data);
                                    break;
                                case 4:
                                    stat.setGender(data);
                                    break;
                                case 5:
                                    stat.setNat(data);
                                    break;
                                case 6:
                                    stat.setDeg(data);
                                    break;
                                case 7:
                                    stat.setGPA(data);
                                    break;
                                case 8:
                                    stat.setSalary(Integer.parseInt(data));
                                    break;
                                default:
                                    System.out.println("invalid data::" + data);
                                    break;
                            }
			index++;
                    }
		index = 0;
		dataList.add(stat);
                      
                try{  
                Class.forName("com.mysql.jdbc.Driver");  
                System.out.println("Driver added..");
                Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/csvreadt","root","1234"); 
                                        
                    PreparedStatement pst = con.prepareStatement("insert into datac (First_Name,Last_Name,Arabic_Name,DOB,Gender,Nationality,Degree,gpa,salary) values(?,?,?,?,?,?,?,?,?)");
                                
                        pst.setString(1,stat.FirstName);
                        pst.setString(2, stat.LastName);
                        pst.setString(3, stat.ArabicName);
                        pst.setString(4, stat.DOB);
                        pst.setString(5, stat.Gender);
                        pst.setString(6, stat.Nationality);
                        pst.setString(7, stat.Degree);
                        pst.setString(8, stat.GPA);
                        pst.setInt(9, stat.Salary);
                        int i = pst.executeUpdate();
                        if(i!=0){
                        System.out.println("added");
                        }
                    else{
                     System.out.println("failed to add");
                     }
                }
    catch (ClassNotFoundException | SQLException e){
        System.out.println("SQLException: " + e.getMessage());
        throw new IllegalStateException("Cannot connect the database!", e);
                    }
		}
                
        while (null !=(line2 = r.readLine()))
        {
            s = new Scanner(line2);
            bufer=line2.split(" ");
            for(String t :bufer)
            {
                if (t.contains(word)){wordCount++;}
                if(t.contains(word2)){wordCount2++;}
            }
            out2.println("There are "+wordCount+ " number of people from the " + word +" Nationality"+"\n"
                    +wordCount+" should be calculated by grouping records on Nationality column"+"\n"
            +"The highest number of people are from "+word+" Nationality and the count is "+wordCount+"\n"
            +"The lowest number of people are from "+word2+" and the count is "+wordCount2+"\n");
        }
        System.setOut(out);
        out.println(dataList);
        out.close();
        out2.close();
	reader.close();
}
       
public static class Data {
    
    private String FirstName;
    private String LastName;
    private String ArabicName;
    private String DOB;
    private String Gender;
    private String Nationality;
    private String Degree;    
    private String GPA;
    private int Salary;
    

	public String getFN() {
		return FirstName;
	}
	public void setFN(String FirstName) {
		this.FirstName = FirstName;
	}
	public String getLN() {
		return LastName;
	}
	public void setLN(String LastName) {
		this.LastName = LastName;
	}
	public String getAN() {
		return ArabicName;
	}
	public void setAN(String ArabicName) {
		this.ArabicName = ArabicName;
	}

         public String getDOB() {
        return DOB;
        }

        public void setDOB(String DOB) {
        this.DOB = DOB;
        }

        public String getGender() {
		return Gender;
	}
	public void setGender(String Gender) {
		this.Gender = Gender;
	}

        public String getNat() {
		return Nationality;
	}
	public void setNat(String Nationality) {
		this.Nationality = Nationality;
	}

        public String getDeg() {
		return Degree;
	}
	public void setDeg(String Degree) {
		this.Degree = Degree;
	}

        public String getGPA() {
		return GPA;
	}
	public void setGPA(String GPA) {
		this.GPA = GPA;
	}

	public int getSalary() {
		return Salary;
	}
	public void setSalary(int Salary) {
		this.Salary = Salary;
	}
        
	
	@Override
	public String toString(){
		return getFN() +" "+ getLN()+" is an " + getNat()+" and has a degree of "+getDeg()+" with a GPA of "+getGPA()+" and is earning " +getSalary() *12 + " yearly. The arabic name is "+getAN() + "\n";
        
    
       	}
        
    }
}
