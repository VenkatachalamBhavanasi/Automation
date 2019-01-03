package dailyprogram;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Code_32 {

	public static void main(String[] args) {
		/*Write a Java program to sort a Map based on the Values.
		
		Input : One=1, Seventeen=17, Twelve=12, Five=5, Two=2, Twenty Five=25
		Output : One=1, Two=2, Five=5, Twelve=12, Seventeen=17, Twenty Five=25	*/
		
		Map<String, Integer> inputMap = new HashMap<String, Integer>();
		inputMap.put("One", 1);
		inputMap.put("Seventeen", 17);
		inputMap.put("Twelve", 11);
		inputMap.put("Twenty Five", 25);
		inputMap.put("Five", 5);
		inputMap.put("Two", 2);
		inputMap.put("Twenty Five", 25);
		
        System.out.println(inputMap);
		Comparator<Object> comp =  new CustomComparator(inputMap);
        Map<String, Integer> outputMap = new TreeMap<String, Integer>(comp);
        outputMap.putAll(inputMap);
        System.out.println(outputMap);
        
        //Alternative way to sort
		/*Set<Entry<String, Integer>> set = inputMap.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Comparator<Entry<String, Integer>> cmp =  new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o1.getValue().compareTo( o2.getValue()) == 0 ? 1 : o1.getValue().compareTo( o2.getValue());
            }
        };
        Collections.sort(list, cmp);
        for(Map.Entry<String, Integer> entry:list){
            System.out.println(entry.getKey()+"="+entry.getValue());
        }*/
	}
}

class CustomComparator implements Comparator<Object> {
	Map<String, Integer> map;
	public CustomComparator(Map<String, Integer> map) {
		this.map = map;
	}
	@Override
	public int compare(Object o1, Object o2) {
		return map.get(o1).compareTo(map.get(o2)) == 0 ? 1 : map.get(o1).compareTo(map.get(o2));
	}
	
}