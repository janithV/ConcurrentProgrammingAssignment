import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student extends Thread{
    private Printer printer;

    public Student(ThreadGroup group, String name, Printer printer) {
        super(group,name);
        this.printer = printer;
    }

    public Printer getPrinter() {
        return printer;
    }

    public Document[] generateStuDocs(){
        Random random = new Random();
        Document[] documents = new Document[5];

        for(int i = 1; i <= 5 ; i++){
            int pages = random.nextInt(10 - 1) + 1;
            String documentName = "DOC_" + i;
            Document doc = new Document(Thread.currentThread().getName(), documentName,pages);
            documents[i-1] = doc;
        }

        return documents;
    }

    public int getTotalPages(Document[] arr){
        int total =0;
        for (Document doc: arr) {
            total += doc.getNumberOfPages();
        }

        return  total;
    }

    @Override
    public void run (){
        Document[] documents = generateStuDocs();
        Random random = new Random();

        for (int i =1 ; i <= documents.length ; i++ ){
            printer.printDocument(documents[i-1]);

            if (i == 5){
                break;
            }
            else{
                int sleepTime = random.nextInt(5000 - 1000) + 1000;
                System.out.println(TextColor.BLUE_TEXT + "[STUDENT]" + TextColor.RESET + " - [" + Thread.currentThread().getName() + "]"
                        + " - will now sleep for " + (sleepTime/1000) + "s");
                try {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }

        }

        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println(TextColor.BLUE_TEXT + "[STUDENT]" + TextColor.RESET + TextColor.GREEN_TEXT + " - ["
                + Thread.currentThread().getName() + "]" + " - Finished printing all documents, Total Documents :" + documents.length
                + ", Total Pages :" + getTotalPages(documents) + TextColor.RESET );
        System.out.println("------------------------------------------------------------------------------------------------");
    }

}
