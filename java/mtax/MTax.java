import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

public class MTax implements Constant {
    
    public MTax(){
        
    }
    
    public static List<String> validate(List<X_Tax> xTaxList) {
        
        List<String> errorList = new ArrayList<>();
        
//        List<String> taxCategoryList = MInfoTaxCategory.getTaxCategoryStringList();
        
        if(xTaxList != null && xTaxList.size() > 0) {
            List<String> validIds = new ArrayList<>();
            for (X_Tax tax : xTaxList) {
                if(tax.getId() != null){
                    validIds.add(tax.getId().toString());
                }
//                if(tax.getAmount() == null) {
//                    errorList.add("El importe es obligatorio");
//                }

                if(tax.getTax() == null) {
                    errorList.add("El impuesto es obligatorio");
                }
//                else if(!taxCategoryList.contains(tax.getTax())) {
//                    errorList.add("El impuesto no es un dato valido");
//                }

//                if(tax.isLocal()){
//                    if(tax.isTrasladado() && tax.getTaxAmount() == null ) {
//                        errorList.add("El importe es obligatorio");
//                    }
//                } 
//                else {
//                    if(tax.getTaxAmount() == null ) {
//                        errorList.add("El importe es obligatorio");
//                    }
//                }
                
                if(!tax.isLocal()){
                    // no need for couter
                    errorList.add("Debe de incluir al menos una tasa no local");   
                }
            }
            if(validIds.size() > 0){
                    
                    List<X_Tax> xt = TaxsByListId(validIds, false);
                    if(xt.size() != validIds.size()){
                        errorList.add("Existen datos no guardados previamente");
                    }else{
                        // We need Only the getCreated date 
                        HashMap<String, Date> map_taxs = new HashMap<String, Date>();
                        for(X_Tax tax: xt){
                            map_taxs.put(tax.getId().toString(), tax.getCreated());
                        }

                        int xTaxListSize= xTaxList.size(); // better to compute the size just one time
                        for(int i = 0; i < xTaxListSize ; i++){
                            if(xTaxList.get(i).getId() != null){
                                String TaxId = xTaxList.get(i).getId().toString();
                                Date createdDate = map_taxs.get(TaxId);
                                xTaxList.get(i).setCreated(createdDate);
                            }
                        }

                    }
            }
        }
//        else {
//            errorList.add("El documento no tiene tasas");
//        }
        
        return errorList;
    }
    
}
