package com.example.streams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Enhanced coding kata on the Stream API with exception handling, generics, and advanced concepts.
 * All methods include proper validation and can be completed with a single return statement plus validation.
 */
public class GentlyDownTheStream {

    protected List<String> fruits;
    protected List<String> veggies;
    protected List<Integer> integerValues;

    public GentlyDownTheStream() {
        fruits = Arrays.asList("Apple", "Orange", "Banana", "Pear", "Peach", "Tomato");
        veggies = Arrays.asList("Corn", "Potato", "Carrot", "Pea", "Tomato");
        integerValues = new Random().ints(0, 1001)
                .boxed()
                .limit(1000)
                .collect(Collectors.toList());
    }

    /**
     * Example method showing proper exception handling and validation
     * Returns a sorted list of fruits with comprehensive error checking
     */
    public List<String> sortedFruits() throws InvalidDataException {
        if (fruits == null) {
            throw new IllegalArgumentException("Fruits collection cannot be null");
        }
        if (fruits.isEmpty()) {
            throw new EmptyCollectionException("Fruits collection cannot be empty");
        }
        if (fruits.stream().anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Fruits collection contains null elements");
        }

        return fruits.stream()
                .sorted()
                .toList();
    }
    /* STUDENT NOTE - I rewrote the sortedFruits() method because this method alone was failing
       2 tests: shouldHandleEmptyCollections and shouldHandleNullCollections, because it would
       only ever throw InvalidDataException when the tests expect EmptyCollectionException or
       IllegalArgumentException.
     */

    /**
     * Enhanced version with custom predicate and exception handling
     */
    public List<String> sortedFruitsException() throws InvalidDataException {
        return sortedFruitsWithFilter(fruit -> !fruit.startsWith("A"));
    }

    // DONE - return a list with the first 2 elements of a sorted list of fruits
    // Add proper validation and exception handling
    public List<String> sortedFruitsFirstTwo() {
        // Implement with validation, null checks, and exception handling
        validateCollection(fruits, "Fruits collection");

        return fruits.stream()
                .sorted()
                .limit(2)
                .toList();
    }

    // DONE - return a comma separated String of sorted fruits
    // Handle null values and empty results gracefully
    public String commaSeparatedListOfFruits() {
        // Implement with proper string joining and validation
        validateCollection(fruits, "Fruits collection");

        return fruits.stream()
                .sorted()
                .collect(Collectors.joining(", "));
    }

    // DONE - return a list of veggies sorted in reverse (descending) order
    // Use Comparator.reverseOrder() and handle edge cases
    public List<String> reverseSortedVeggies() {
        validateCollection(veggies, "Veggies collection");

        return veggies.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    // DONE - return a list of veggies sorted in reverse order, all in upper case
    // Chain multiple stream operations with proper exception handling
    public List<String> reverseSortedVeggiesInUpperCase() {
        validateCollection(veggies, "Veggies collection");

        return veggies.stream()
                .sorted(Comparator.reverseOrder())
                .map(v -> v.toUpperCase(Locale.ROOT))
                .toList();
    }

    // DONE - return a list of the top 10 values in the list of random integers
    // Handle cases where list has fewer than 10 elements
    public List<Integer> topTen() {
        validateCollection(integerValues, "Integer values collection");

        return integerValues.stream()
                .sorted(Comparator.reverseOrder())
                .limit(10)
                .toList();
    }

    // DONE - return a list of the top 10 unique values in the list of random integers
    // Use distinct() operation and handle empty results
    public List<Integer> topTenUnique() {
        validateCollection(integerValues, "Integer values collection");

        return integerValues.stream()
                .sorted(Comparator.reverseOrder())
                .distinct()
                .limit(10)
                .toList();
    }

    // DONE - return a list of the top 10 unique values that are odd
    // Combine filtering, distinct, and limiting operations
    public List<Integer> topTenUniqueOdd() {
        validateCollection(integerValues, "Integer values collection");

        return integerValues.stream()
                .sorted(Comparator.reverseOrder())
                .distinct()
                .filter(integer -> integer % 2 != 0)
                .limit(10)
                .toList();
    }

    // DONE - return the average of all random numbers
    // Handle potential OptionalDouble and division by zero scenarios
    public Double average() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");
        } catch (EmptyCollectionException e) {
            throw new InvalidDataException(e.getMessage());
        }

        return integerValues.stream()
                .mapToInt(i -> i)
                .average()
                .orElse(0.0);
    }

    // Generic method for safe collection operations
    private <T> void validateCollection(Collection<T> collection, String collectionName) throws EmptyCollectionException {
        if (collection == null) {
            throw new IllegalArgumentException(collectionName + " cannot be null");
        }
        if (collection.isEmpty()) {
            throw new EmptyCollectionException(collectionName + " cannot be empty");
        }
    }

    // Helper method demonstrating advanced generics and functional programming
    private <T> List<T> sortedWithFilter(Collection<T> collection,
                                         Predicate<T> filter,
                                         Comparator<T> comparator) throws InvalidDataException {
        try {
            validateCollection(collection, "Input collection");

            return collection.stream()
                    .filter(Objects::nonNull)
                    .filter(filter)
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InvalidDataException("Failed to sort and filter collection: " + e.getMessage());
        }
    }

    // Specialized method using the generic helper
    private List<String> sortedFruitsWithFilter(Predicate<String> filter) throws InvalidDataException {
        return sortedWithFilter(fruits, filter, String::compareTo);
    }

    // Utility method for safe integer operations
    private OptionalDouble safeAverage(Collection<Integer> numbers) {
        return numbers.stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average();
    }
}