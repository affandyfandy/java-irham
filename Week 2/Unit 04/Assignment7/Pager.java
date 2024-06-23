import java.util.List;

public class Pager<T> {
    private List<T> data;
    private int pageSize;
    private int totalItems;
    private int currentPage;

    public Pager(List<T> data, int pageSize) {
        this.data = data;
        this.pageSize = pageSize;
        this.totalItems = data.size();
        this.currentPage = 1;
    }
    

    // Method to fetch and display current page of data
    public void showCurrentPage() {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);

        List<T> currentPageData = data.subList(startIndex, endIndex);
        System.out.println("Page " + currentPage + ": " + currentPageData);
    }

    // Method to navigate to next page
    public void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
            showCurrentPage();
        } else {
            System.out.println("Already on the last page.");
        }
    }

    // Method to navigate to previous page
    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            showCurrentPage();
        } else {
            System.out.println("Already on the first page.");
        }
    }

    // Method to jump to a specific page number
    public void goToPage(int pageNumber) {
        if (pageNumber >= 1 && pageNumber <= getTotalPages()) {
            currentPage = pageNumber;
            showCurrentPage();
        } else {
            System.out.println("Invalid page number.");
        }
    }

    // Helper method to calculate total number of pages
    private int getTotalPages() {
        return (int) Math.ceil((double) totalItems / pageSize);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.totalItems = data.size();
        currentPage = 1; // Reset to first page when data changes
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public static void main(String[] args) {
        // Example usage with a list of Integer objects
        List<Integer> integerList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Pager<Integer> integerPager = new Pager<>(integerList, 3);

        // Display first page
        System.out.println("Initial page:");
        integerPager.showCurrentPage();

        // Navigate to next page
        System.out.println("\nNext page:");
        integerPager.nextPage();

        // Navigate to specific page
        System.out.println("\nGo to page 3:");
        integerPager.goToPage(3);

        // Navigate to previous page
        System.out.println("\nPrevious page:");
        integerPager.previousPage();

        // Change data and reset pager
        List<Integer> newIntegerList = List.of(11, 12, 13, 14, 15, 16);
        integerPager.setData(newIntegerList);
        integerPager.setPageSize(4);

        // Display first page of new data
        System.out.println("\nChanged data - Initial page:");
        integerPager.showCurrentPage();
    }
}
