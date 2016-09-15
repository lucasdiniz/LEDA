package adt.bst;

import adt.bt.BTNode;

import java.util.ArrayList;
import java.util.List;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;
	protected final BSTNode<T> emptyNode;

	public BSTImpl() {
		root = new BSTNode<T>();
		emptyNode = new BSTNode<>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {

		return height(this.root);
	}

	public int height(BSTNode<T> node) {

		if (node.isEmpty()) {
			return -1;
		}

		int heightLeft = height((BSTNode<T>) node.getLeft());
		int heightRight = height((BSTNode<T>) node.getRight());

		return 1 + Math.max(heightLeft, heightRight);
	}

	@Override
	public BSTNode<T> search(T element) {

		if (element == null) {
			return emptyNode;
		}

		return search(this.root, element);
	}

	public BSTNode<T> search(BSTNode<T> node, T element) {

		if (node.isEmpty()) {
			return emptyNode;
		}

		T nodeData = node.getData();

		if (nodeData.equals(element)) {

			return node;

		} else if (nodeData.compareTo(element) > 0) { //element eh menor, procuraremos esquerda

			return search((BSTNode<T>) node.getLeft(), element);

		} else { //element eh maior, procuraremos a direita

			return search((BSTNode<T>) node.getRight(), element);

		}

	}

	//Ao inserir lembrar de instanciar os filhos como nos vazios
	@Override
	public void insert(T element) {

		if (element != null) {
			BSTNode<T> nilParent = new BSTNode<>(); //Para o caso de estarmos inserindo a raiz (que tem parent NIL)
			insert(this.root, element, nilParent);
		}

	}

	protected void insert(BSTNode<T> node, T element, BSTNode<T> parent) {

		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
			return;
		}

		T nodeData = node.getData();

		if (nodeData.equals(element)) {

			return;

		} else if (nodeData.compareTo(element) > 0) {

			insert((BSTNode<T>) node.getLeft(), element, node);

		} else {

			insert((BSTNode<T>) node.getRight(), element, node);

		}

	}

	@Override
	public BSTNode<T> maximum() {

		if (isEmpty()) {
			return null;
		}

		return maximum(this.root);

	}

	protected BSTNode<T> maximum(BSTNode<T> node) {

		if (!node.getRight().isEmpty()) {

			return maximum((BSTNode<T>) node.getRight());

		} else {

			return node;

		}
	}

	@Override
	public BSTNode<T> minimum() {

		if (isEmpty()) {
			return null;
		}

		return minimum(this.root);
	}

	protected BSTNode<T> minimum(BSTNode<T> node) {

		if (!node.getLeft().isEmpty()) {

			return minimum((BSTNode<T>) node.getLeft());

		} else {

			return node;

		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = search(element);

		if (node.equals(emptyNode)) {
			return null;
		}

		if (!node.getRight().isEmpty()) {
			return minimum((BSTNode<T>) node.getRight());
		}

		else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			while (!parent.isEmpty() && node.equals(parent.getRight())) {
				node = (BSTNode<T>) node.getParent();
				parent = (BSTNode<T>) parent.getParent();
			}

			return (parent.equals(emptyNode)) ? null : parent;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = search(element);

		if (node.equals(emptyNode)) {
			return null;
		}

		if (!node.getLeft().isEmpty()) {
			return maximum((BSTNode<T>) node.getLeft());
		}

		else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			while (!parent.isEmpty() && node.equals(parent.getLeft())) {
				node = (BSTNode<T>) node.getParent();
				parent = (BSTNode<T>) parent.getParent();
			}

			return (parent.equals(emptyNode)) ? null : parent;
		}
	}

	@Override
	public void remove(T element) {

		if (isEmpty() || element == null)
			return;

		BTNode<T> node = search(element);

		if(!node.isEmpty())
			remove(node);

	}

	public void remove(BTNode<T> node) {

		if (node.isLeaf()) {
			node.setData(null);
		} else {

			if (!node.getRight().isEmpty()) {

				BSTNode<T> minNode = minimum((BSTNode<T>) node.getRight());
				node.setData(minNode.getData());
				remove(minNode);

			} else {

				BSTNode<T> maxNode = maximum((BSTNode<T>) node.getLeft());
				node.setData(maxNode.getData());
				remove(maxNode);
			}

		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public T[] preOrder() {
		if (isEmpty()) {
			T[] result = (T[]) new Comparable[0];
			return result;
		}

		List<T> preOrderList = new ArrayList<>();
		preOrder(this.root, preOrderList);

		return (T[]) preOrderList.toArray(new Comparable[preOrderList.size()]);
	}

	private void preOrder(BSTNode<T> node, List<T> preOrderList) {
		if (node.isEmpty())
			return;

		preOrderList.add(node.getData());
		preOrder((BSTNode<T>) node.getLeft(), preOrderList);
		preOrder((BSTNode<T>) node.getRight(), preOrderList);

	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] order() {
		if (isEmpty()) {
			T[] result = (T[]) new Comparable[0];
			return result;
		}

		List<T> orderList = new ArrayList<>();
		order(this.root, orderList);

		return (T[]) orderList.toArray(new Comparable[orderList.size()]);

	}

	private void order(BSTNode<T> node, List<T> orderList) {

		if (node.isEmpty())
			return;

		order((BSTNode<T>) node.getLeft(), orderList);
		orderList.add(node.getData());
		order((BSTNode<T>) node.getRight(), orderList);

	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] postOrder() {
		if (isEmpty()) {
			T[] result = (T[]) new Comparable[0];
			return result;
		}

		List<T> postOrderList = new ArrayList<>();
		postOrder(this.root, postOrderList);

		return (T[]) postOrderList.toArray(new Comparable[postOrderList.size()]);
	}

	private void postOrder(BSTNode<T> node, List<T> postOrderList) {
		if (node.isEmpty())
			return;

		postOrder((BSTNode<T>) node.getLeft(), postOrderList);
		postOrder((BSTNode<T>) node.getRight(), postOrderList);
		postOrderList.add(node.getData());
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
