module org.main.UAP {
    // Export your packages to make them accessible to other modules
    exports org.main.UAP;

    // Add a dependency on the java.desktop module for javax.swing
    requires java.desktop;
}
