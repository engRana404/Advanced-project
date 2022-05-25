
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
//Apache
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Main {
    public static void main(String[] args) throws IOException {

        //Create a file with our wanted path
        File file = new File("C:/Users/pc/Downloads/Example.xlsx");
        //raw data from the file
        FileInputStream fis = new FileInputStream(file);
        //convert the data into workbook format
        Workbook workbook = new XSSFWorkbook(fis);
        //we want to get the first sheet of the workbook
        Sheet  firstSheet = workbook.getSheetAt(0);
        //we need an object to iterate around the sheet we have
        Iterator<Row> iterator = firstSheet.iterator();
        //we make an array list for fields and other for parents
        ArrayList <Field> fields = new ArrayList<>();
        ArrayList <Parent> parents = new ArrayList<>();
        //While iterator still has rows we will iterate on them
        while (iterator.hasNext()) {
            Row nextRow = iterator.next(); //get that row
            //we will iterate on the cells of each row
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            // add a new object to the fields array
            fields.add(new Field());
            //add a new object to the parents array
            parents.add(new Parent());
            //****************************Here we make two objects each row/ we want to make only one*****************************************************

            //While there are cells
            while (cellIterator.hasNext()) {
                //iterate on next cell
                Cell cell = cellIterator.next();
                //Here we get the string value of the cell
                String Scell = cell.getStringCellValue();
                //Here we want to see what we will do on each cell(check its value and add it to classes etc...
                //if we reach our
                if(Scell.equals("I")|| Scell.equals("O")){
                    while (cellIterator.hasNext()) {
                        Cell wantedCell = cellIterator.next();
                        String WantedSCell = wantedCell.getStringCellValue();

                        if(nextRow.getCell(2).getStringCellValue().equals("string")){
                            switch(wantedCell.getColumnIndex()){
                                case 1: fields.get(nextRow.getRowNum()).setField_name(WantedSCell);
                                case 2: fields.get(nextRow.getRowNum()).setType(WantedSCell);
                                case 3: fields.get(nextRow.getRowNum()).setAllowed_value(WantedSCell);
                                case 4: fields.get(nextRow.getRowNum()).setMandatory(WantedSCell);}
                        }
                        else {
                            switch(wantedCell.getColumnIndex()){
                                case 1: parents.get(nextRow.getRowNum()).setField_name(WantedSCell);
                                case 2: parents.get(nextRow.getRowNum()).setType(WantedSCell);
                                case 3: parents.get(nextRow.getRowNum()).setAllowed_value(WantedSCell);
                                case 4: parents.get(nextRow.getRowNum()).setMandatory(WantedSCell);

                            }
                        }

                }


                }

            }
            System.out.println();
        }

        workbook.close();
        fis.close();
    }
} //End of main class
 class Field {

    private String Field_name=null;
    private String allowed_value=null;
    private String mandatory=null;
    private String type=null;

    private Parent parent; //here we put the parent of our field (there might be none)


    //No arg constructor
    public  Field()
    {
    }

    public void setField_name(String field_name) {
        Field_name = field_name;
    }

    public void setAllowed_value(String allowed_value) {
        this.allowed_value = allowed_value;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }
    public void setType(String type) {
        this.type = type;
    }


     public String getField_name() {
         return Field_name;
     }

     public String getAllowed_value() {
         return allowed_value;
     }

     public String getMandatory() {
         return mandatory;
     }

     public String getType() {
         return type;
     }

     //return true of the field has no parents and false if not
     public boolean hasNOParents(){
         return this.Field_name.startsWith("/field"); //if there's no object before the field name

     }
     public void getParent(){ //this function should return the name of the fields parent ******Needs Check*******
       String [] arr;
       arr= this.Field_name.split("/"); //Split /object1/object2/field6 into [object1, object2,field6]
         parent.setField_name(arr[-2]); //the name before field name is the parent and the name before it is the parent of the parent (needs handling)
         //**********Array index is out of bounds needs to be fixed************
         //We need to connect this parent to an object we already made not make a new one

     }

     public void setParent(Parent parent) {
         this.parent = parent;
     }
 } //End of Field Class
class Parent{
    //Data fields
    private String Field_name=null;
    private String allowed_value=null;
    private String mandatory=null;
    private String type=null;

    //no arg constructor
    public Parent() {

    }

    public void setField_name(String field_name) {
        Field_name = field_name;
    }

    public void setAllowed_value(String allowed_value) {
        this.allowed_value = allowed_value;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField_name() {
        return Field_name;
    }

    public String getAllowed_value() {
        return allowed_value;
    }

    public String getMandatory() {
        return mandatory;
    }

    public String getType() {
        return type;
    }
}

