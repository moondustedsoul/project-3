I am working on a Java project using Test Driven Development. My current code fails the following 3 tests:

@Test
@DisplayName("Should handle empty collections appropriately")
void shouldHandleEmptyCollections() {
// Test empty collection behavior
assertThatThrownBy(() -> {
GentlyDownTheStream emptyStream = new GentlyDownTheStream();
emptyStream.fruits = List.of(); // Empty list
emptyStream.sortedFruits();
}).isInstanceOf(EmptyCollectionException.class)
.hasMessageContaining("cannot be empty");
}

@Test
@DisplayName("Should handle null collections gracefully")
void shouldHandleNullCollections() {
// Test would require creating instance with null collections
// This demonstrates the type of exception testing to implement
assertThatThrownBy(() -> {
GentlyDownTheStream nullStream = new GentlyDownTheStream();
// Force null state for testing
nullStream.fruits = null;
nullStream.sortedFruits();
}).isInstanceOf(IllegalArgumentException.class)
.hasMessageContaining("cannot be null");
}

@Test
@DisplayName("Should handle division by zero in average calculation")
void shouldHandleDivisionByZeroInAverage() {
assertThatThrownBy(() -> {
GentlyDownTheStream emptyIntStream = new GentlyDownTheStream();
emptyIntStream.integerValues = List.of();
emptyIntStream.average();
}).isInstanceOf(InvalidDataException.class);
}

The methods I implemented are as follows:

// DONE - return a list with the first 2 elements of a sorted list of fruits
// Add proper validation and exception handling
public List<String> sortedFruitsFirstTwo() throws InvalidDataException {
// Implement with validation, null checks, and exception handling
if (fruits == null) {
throw new InvalidDataException("Fruits collection is null");
}

        if (fruits.stream().anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Fruits collection contains null elements");
        }

        return fruits.stream()
                .sorted()
                .limit(2)
                .toList();
    }

    // DONE - return a comma separated String of sorted fruits
    // Handle null values and empty results gracefully
    public String commaSeparatedListOfFruits() throws InvalidDataException {
        // Implement with proper string joining and validation
        if (fruits == null) {
            throw new InvalidDataException("Fruits collection is null");
        }

        if (fruits.stream().anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Fruits collection contains null elements");
        }

        return fruits.stream()
                .sorted()
                .collect(Collectors.joining(", "));
    }

    // DONE - return a list of veggies sorted in reverse (descending) order
    // Use Comparator.reverseOrder() and handle edge cases
    public List<String> reverseSortedVeggies() throws InvalidDataException {
        if (veggies == null) {
            throw new InvalidDataException("Veggies collection is null");
        }

        if (veggies.stream().anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Veggies collection contains null elements");
        }

        return veggies.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    // DONE - return a list of veggies sorted in reverse order, all in upper case
    // Chain multiple stream operations with proper exception handling
    public List<String> reverseSortedVeggiesInUpperCase() throws InvalidDataException {
        if (veggies == null) {
            throw new InvalidDataException("Veggies collection is null");
        }

        if (veggies.stream().anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Veggies collection contains null elements");
        }

        return veggies.stream()
                .sorted(Comparator.reverseOrder())
                .map(v -> v.toUpperCase(Locale.ROOT))
                .toList();
    }

    // DONE - return a list of the top 10 values in the list of random integers
    // Handle cases where list has fewer than 10 elements
    public List<Integer> topTen() throws InvalidDataException {
        if (integerValues == null) {
            throw new InvalidDataException("Integer values collection is null");
        }

        if (integerValues.stream().anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Integer values collection contains null elements");
        }

        return integerValues.stream()
                .sorted(Comparator.reverseOrder())
                .limit(10)
                .toList();
    }

    // DONE - return a list of the top 10 unique values in the list of random integers
    // Use distinct() operation and handle empty results
    public List<Integer> topTenUnique() throws InvalidDataException {
        if (integerValues == null) {
            throw new InvalidDataException("Integer values collection is null");
        }

        if (integerValues.stream().anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Integer values collection contains null elements");
        }

        return integerValues.stream()
                .sorted(Comparator.reverseOrder())
                .distinct()
                .limit(10)
                .toList();
    }

    // DONE - return a list of the top 10 unique values that are odd
    // Combine filtering, distinct, and limiting operations
    public List<Integer> topTenUniqueOdd() throws InvalidDataException {
        if (integerValues == null) {
            throw new InvalidDataException("Integer values collection is null");
        }

        if (integerValues.stream().anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Integer values collection contains null elements");
        }
