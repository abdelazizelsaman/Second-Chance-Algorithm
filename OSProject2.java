package os.project2;

import java.util.Scanner;

public class OSProject2 {

    //number of frames
    static int  numberOfFrames; 
    //actual frames
    static char [] frame ; 
    //reference bit for each frame
    static boolean [] referenceBit ;
    //sequence of reference string
    static String sequence = new String();
    //number of page faults
    static int pageFault = 0;
    //current page string
    static char page;
    //flag to check if pointer should point to 1st frame (queue rotation)
    static boolean flag = false;
    //scanner to get input from user
    Scanner sc = new Scanner(System.in);
    
    
    
    // second chance algorithm implementation 
    public void arrayTraverse()
    {
        //run the algorithm till the reference string ends
        while(!sequence.isEmpty()){
            for(int pointer=0; pointer<numberOfFrames; pointer++)
            {
                //make sure reference string is not empty
                if(!sequence.isEmpty()){
                    
                    //check if pointer finishes one complete cycle on queue
                    if(flag)
                    {
                        //set pointer to 1st frame in queue
                        pointer=0;
                        flag = false;
                    }
                    //load 1st page from reference string
                    page = sequence.charAt(0);
                    //change queue of frames to string to check if page already exsists in queue
                    String test = new String(frame);
                    //returns -1 if page doesn't exsist in the queue
                    int t = test.indexOf(page);  

                    //check if the frame is empty
                    if(frame[pointer] == 0 && t == -1)
                    {
                        //add page to frame
                        addPage(pointer);   
                    }
                    //check if the page already exists
                    else if(t != -1)
                    {

                      //set corresponding reference bit into true
                      referenceBit[t]=true;
                      //decrement the pointer to stay on the same frame
                      if (pointer == 0){
                          flag = true;
                      }else
                      {
                          pointer--;
                      }

                      //remove page from reference string
                      sequence=sequence.substring(1);

                    }

                    //check if the old page has a second chance
                    else if(referenceBit[pointer]==true)
                    {
                        //set the corresponding reference bit to false                    
                        referenceBit[pointer]=false;
                        //break out of the current loop & check next frame
                        printFrames(page, pointer+1);
                        continue;
                    }

                    //page doesn't exsist in frame, exsisting page has no second chance 
                    else    
                    {
                        //add candidate page to the frame, remove old page
                        addPage(pointer);
                    }
                    printFrames(page, (flag == true) ? pointer : pointer+1 );

                }
            }       
        
        }
    }// arrayTraverse
    
    public void addPage(int index)
    {
        //add page to frame
        frame[index] = page;
        //set corresponding reference bit into false
        referenceBit[index]=false;
        //remove page from reference string
        sequence=sequence.substring(1);
        //increment page faults
        pageFault++;
    }
    
    public void inputReferenceString()
    {
        System.out.println("Please enter the reference string");
        sequence = sc.next();
    }
    
    public void inputNumberOfFrames()
    {
        System.out.println("Please enter the number of frames");
        numberOfFrames = sc.nextInt();
        frame = new char[numberOfFrames];
        referenceBit = new boolean[numberOfFrames];
    }
    
    public void printFrames(char page, int pointer)
    {
        System.out.println("For Page: "+page);
        System.out.println(" -----------");
        for(int i=0; i<frame.length; i++)
        {
            System.out.print("| "+frame[i]+" | "+referenceBit[i]+" |");
            if(i == pointer || (pointer == frame.length && i==0)){
                System.out.print("<--"+"\n");
            }else{
                System.out.print("\n");
            }
            System.out.println(" -----------");
        }
        System.out.println("\n");
        System.out.println("\n");
    }
    
    
    public static void main(String args[]){
        OSProject2 s = new OSProject2();
        s.inputReferenceString();
        s.inputNumberOfFrames();
        s.arrayTraverse();
        System.out.println("Page Faults: "+pageFault);
    }  
}
    

