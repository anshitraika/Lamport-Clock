
package lamportclock;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;


public class Lamportclock{
     static Scanner sc=new Scanner(System.in);
     static int d;
    public static void main(String[] args) {
        
        int p;
        int e;
        System.out.println("Enter the value of d:");
        d=sc.nextInt();
        System.out.println("Enter the no. of processes:");
        p=sc.nextInt();
        System.out.println("Enter the max. no of events:");
        e=sc.nextInt();
        int eventarr[]=new int[p];
        for(int i=0;i<p;i++){
            System.out.println("Enter the event for process "+(i+1)+":");
            eventarr[i]=sc.nextInt();
        }
        int arr[][]=new int[p][e+1];
        initialisation(arr,p,e+1);
        HashMap<String,String>map=new HashMap<>();
        hbr(map, arr);
        System.out.println("HBR are:-");
        System.out.println(map);
        Computation(arr,map,p,e+1,0);
        display(p,e+1,arr,eventarr);


        
    }
    
    static void initialisation(int arr[][],int p,int e){
       // int k;
        for(int i=0;i<p;i++){
         //   k=0;
            for(int j=0;j<e;j++){
                arr[i][j]=0;
            }
        }
    }
    
    static void hbr(HashMap<String,String>map,int[][] arr){
        System.out.println("Enter total no. of hbr:");
        int line=sc.nextInt();
        String sp,rp,se,re,sender="",reciever="";
        while(line-->0){
            System.out.println("Enter the sender process:");
            sp=sc.next();
            System.out.println("Enter the sender event:");
            se=sc.next();
            sender=sp+se;
            System.out.println("Enter the reciever process:");
            rp=sc.next();
            System.out.println("Enter the reciever event:");
            re=sc.next();
            reciever=rp+re;
            map.put(sender,reciever);            
        }
    }


    
    static void update(int[][]arr,int i,int j,String sender){
        int se=Integer.valueOf(sender);
        int event=se%10;
        int process=se/10;
        int timestamp=arr[process-1][event];
        arr[i][j]=Math.max(arr[i][j-1]+d,timestamp+d);
 
    }
    
     static void Computation(int[][] arr, HashMap<String, String> map,int p,int e,int process) {
       
        Set<String> keys=map.keySet();
        ArrayList<String>recset=new ArrayList<>();
        ArrayList<String>senderset=new ArrayList<>();
        for(String ke:keys)
        {recset.add(map.get(ke));
         senderset.add(ke);
        }
         for(int i=1;i<e;i++){
            String rec=String.valueOf(process+1)+String.valueOf(i);
                if(recset.contains(rec)){                 //if event is a reciever
                    String sender=senderset.get(recset.indexOf(rec));
                   // System.out.println("Reciever:"+rec);
                     int se=Integer.valueOf(sender);
                     int event=se%10;
                     int sender_pro=se/10;
                     if(arr[sender_pro-1][event]==0){
                       //  System.out.println("sender:"+sender_pro);
                         Computation(arr,map,p,e,sender_pro-1);
                         update(arr,process,i,sender);
                     }
                     else{
                         update(arr,process,i,sender);
                     }
                }
                else{
                    //IR1 implementation
                    arr[process][i]=arr[process][i-1]+d;
                   
                }
            
         }
     }

     static void display(int p,int e,int[][]arr,int[]eventarr) {
        for(int i=0;i<p;i++){
          System.out.println("");  
          System.out.print("P"+(i+1)+":");
            for(int j=1;j<=eventarr[i];j++)
                System.out.print(" "+arr[i][j]);
        }
    }

}
