import java.util.*;
import java.util.Map.Entry;

public class AppleDistribution {

    public static Map<String, List<Integer>> distributeApples(List<Integer> appleWeights, Map<String, Integer> contributions) {
        int totalWeight = appleWeights.stream().mapToInt(Integer::intValue).sum();
        int totalContributions = contributions.values().stream().mapToInt(Integer::intValue).sum();

        Map<String, Double> targetDistribution = new HashMap<>();
        for (Entry<String, Integer> entry : contributions.entrySet()) {
            targetDistribution.put(entry.getKey(), totalWeight * (entry.getValue() / (double) totalContributions));
        }

        Map<String, Double> actualDistribution = new HashMap<>();
        for (String person : contributions.keySet()) {
            actualDistribution.put(person, 0.0);
        }

        Map<String, List<Integer>> distribution = new HashMap<>();
        for (String person : contributions.keySet()) {
            distribution.put(person, new ArrayList<>());
        }

        appleWeights.sort(Collections.reverseOrder());
        List<String> personList = new ArrayList<>(contributions.keySet());
        personList.sort((p1, p2) -> contributions.get(p2) - contributions.get(p1));

        for (int weight : appleWeights) {
            boolean allocated = false;
            for (String person : personList) {
                if (actualDistribution.get(person) + weight <= targetDistribution.get(person)) {
                    actualDistribution.put(person, actualDistribution.get(person) + weight);
                    distribution.get(person).add(weight);
                    allocated = true;
                    break;
                }
            }
            if (!allocated) {
                String lastPerson = personList.get(personList.size() - 1);
                actualDistribution.put(lastPerson, actualDistribution.get(lastPerson) + weight);
                distribution.get(lastPerson).add(weight);
            }
        }

        return distribution;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> appleWeights = new ArrayList<>();
        
        while (true) {
            System.out.print("Enter apple weights in grams (-1 to stop): ");
            int weight = scanner.nextInt();
            if (weight == -1) {
                break;
            }
            appleWeights.add(weight);
        }

        Map<String, Integer> contributions = new LinkedHashMap<>();
        contributions.put("Ram", 50);
        contributions.put("Sham", 30);
        contributions.put("Rahim", 20);

        Map<String, List<Integer>> distribution = distributeApples(appleWeights, contributions);

        System.out.println("Distribution Result:");
        List<String> outputOrder = Arrays.asList("Ram", "Sham", "Rahim");
        for (String person : outputOrder) {
            System.out.println(person + ": " + distribution.get(person) + "g");
        }
    }
}
