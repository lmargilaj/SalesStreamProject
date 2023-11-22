// scenes/MenuScene.java
package scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scenes.logic.Items;
import scenes.logic.Products;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ListView;

public class MenuScene {

    private Stage primaryStage;
    private String username;
    private List<Items.Item> orderList = new ArrayList<>();
    private ListView<String> orderListView = new ListView<>();
    private CreateReceipt receipt;
    private Pane menuLayout;

    public MenuScene(Stage primaryStage, String username) {
        this.primaryStage = primaryStage;
        this.username = username;
        this.receipt = new CreateReceipt();
    }

    public void show() {
        Button logoutBtn = new Button("Logout");
        logoutBtn.layoutXProperty().bind(primaryStage.widthProperty().subtract(logoutBtn.widthProperty()));
        logoutBtn.setLayoutY(0);
        logoutBtn.setOnAction(e -> switchToLoginScene());

        menuLayout = new Pane();
        menuLayout.setPadding(new Insets(25, 25, 25, 25));

        // Style rectangle where receipt will be shown
        Rectangle rectangle = new Rectangle();
        rectangle.widthProperty().bind(primaryStage.widthProperty().multiply(0.3));
        rectangle.heightProperty().bind(primaryStage.heightProperty().multiply(1));
        rectangle.setFill(Color.GRAY);

        menuLayout.getChildren().add(rectangle);

        // Create items
        Items.Item item1 = Products.item1;
        Items.Item item2 = Products.item2;
        Items.Item item3 = Products.item3;
        Items.Item item4 = Products.item4;
        Items.Item item5 = Products.item5;
        Items.Item item6 = Products.item6;
        Items.Item item7 = Products.item7;
        Items.Item item8 = Products.item8;
        Items.Item item9 = Products.item9;

        // Create buttons array
        Button[] buttons = new Button[9];

        // Create buttons and associate them with items
        buttons[0] = createButton(menuLayout, item1, 0.325, 0.25, "menu-button");
        buttons[1] = createButton(menuLayout, item2, 0.525, 0.25, "menu-button");
        buttons[2] = createButton(menuLayout, item3, 0.725, 0.25, "menu-button");

        buttons[3] = createButton(menuLayout, item4, 0.325, 0.35, "menu-button");
        buttons[4] = createButton(menuLayout, item5, 0.525, 0.35, "menu-button");
        buttons[5] = createButton(menuLayout, item6, 0.725, 0.35, "menu-button");

        buttons[6] = createButton(menuLayout, item7, 0.325, 0.45, "menu-button");
        buttons[7] = createButton(menuLayout, item8, 0.525, 0.45, "menu-button");
        buttons[8] = createButton(menuLayout, item9, 0.725, 0.45, "menu-button");

        // Add the order list to the layout
        menuLayout.getChildren().add(orderListView);

        // Create a "Finish" button
        Button finishBtn = new Button("Finish");
        finishBtn.setOnAction(e -> switchToReceiptScene());
        finishBtn.prefWidthProperty().bind(menuLayout.widthProperty().multiply(0.15));
        finishBtn.prefHeightProperty().bind(menuLayout.heightProperty().multiply(0.05));
        finishBtn.layoutXProperty().bind(menuLayout.widthProperty().multiply(0.8));
        finishBtn.layoutYProperty().bind(menuLayout.heightProperty().multiply(0.9));
        finishBtn.getStyleClass().add("menu-button"); // Add your CSS style class if needed

        // Add the "Finish" button to the layout
        menuLayout.getChildren().add(finishBtn);

        Scene menuScene = new Scene(menuLayout, 600, 500);
        menuScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(menuScene);
    }

    private Button createButton(Pane layout, Items.Item item, double x, double y, String styleClass) {
        double paddingX = 50;
        double paddingY = 50;

        // Style and position buttons
        Button button = new Button(item.getItemName());
        button.setOnAction(e -> handleButtonClick(item));
        button.prefWidthProperty().bind(layout.widthProperty().multiply(0.15)); // Set the preferred width as a
                                                                                // percentage of the layout's width
        button.prefHeightProperty().bind(layout.heightProperty().multiply(0.05)); // Set the preferred height as a
                                                                                  // percentage of the layout's height
        button.layoutXProperty().bind(layout.widthProperty().multiply(x).add(paddingX));
        button.layoutYProperty().bind(layout.heightProperty().multiply(y).add(paddingY));
        button.getStyleClass().add(styleClass);
        layout.getChildren().add(button);

        // Create a Remove Item button
        Button removeItemBtn = new Button("Remove Selected Item");
        removeItemBtn.setOnAction(e -> removeSelectedItem());
        removeItemBtn.prefWidthProperty().bind(layout.widthProperty().multiply(0.15)); // Set the preferred width as a
        // percentage of the layout's width
        removeItemBtn.prefHeightProperty().bind(layout.heightProperty().multiply(0.05)); // Set the preferred height as
                                                                                         // a
        // percentage of the layout's height
        removeItemBtn.layoutXProperty().bind(layout.widthProperty().multiply(x).add(paddingX));
        removeItemBtn.layoutYProperty().bind(layout.heightProperty().multiply(y).add(paddingY));
        removeItemBtn.getStyleClass().add(styleClass);
        layout.getChildren().add(removeItemBtn);

        return button;
    }

    private void handleButtonClick(Items.Item item) {
        // Add the selected item to the order list
        orderList.add(item);

        // Add the selected item to the receipt
        receipt.addItemToReceipt(item);

        // Update the UI
        updateOrderListView();

        // Update receipt display
        receipt.updateReceiptDisplay();

        System.out.println(item.getItemName() + " added to the order! Price: $" + item.getItemPrice());
    }

    private void updateOrderListView() {
        // Clear the existing items in the order list view
        orderListView.getItems().clear();

        // Add the items from the order list to the order list view
        for (Items.Item orderedItem : orderList) {
            orderListView.getItems().add(orderedItem.getItemName() + " - $" + orderedItem.getItemPrice());
        }
    }

    private void removeSelectedItem() {
        // Get the selected item in the order list view
        String selectedItem = orderListView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Remove the item from the order list
            orderList.removeIf(item -> (item.getItemName() + " - $" + item.getItemPrice()).equals(selectedItem));

            // Update the order list view
            updateOrderListView();
        }
    }

    private void switchToReceiptScene() {
        ReceiptScene.showReceipt(primaryStage, orderList);
    }

    private void switchToLoginScene() {
        LoginScene loginScene = new LoginScene(primaryStage);
        loginScene.createLoginScene();
    }
}
