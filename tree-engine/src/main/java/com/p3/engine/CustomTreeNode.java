package com.p3.engine;

public class CustomTreeNode {
    private Long id;
    private String value;
    private CustomTreeNode parent;
    private CustomTreeNode firstChild;
    private CustomTreeNode nextSibling;

    public CustomTreeNode(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public CustomTreeNode getParent() { return parent; }
    public void setParent(CustomTreeNode parent) { this.parent = parent; }
    public CustomTreeNode getFirstChild() { return firstChild; }
    public void setFirstChild(CustomTreeNode firstChild) { this.firstChild = firstChild; }
    public CustomTreeNode getNextSibling() { return nextSibling; }
    public void setNextSibling(CustomTreeNode nextSibling) { this.nextSibling = nextSibling; }
}