package adt.bt;

import adt.bst.BST;
import adt.bst.BSTNode;

public class Util {

	/**
	 * A rotacao a esquerda em node deve subir o seu filho a direita e retorna-lo em seguida
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> pivot = (BSTNode<T>) node.getRight();

		pivot.setParent(node.getParent());

		if (!node.getParent().isEmpty()) {

			if (node.getParent().getLeft().equals(node)) {
				node.getParent().setLeft(pivot);
			} else {
				node.getParent().setRight(pivot);
			}
		}

		node.setRight(pivot.getLeft());
		node.getRight().setParent(node);

		node.setParent(pivot);
		pivot.setLeft(node);

		return pivot;
	}

	/**
	 * A rotacao a direita em node deve subir seu filho a esquerda s retorna-lo em seguida
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {

		BSTNode<T> pivot = (BSTNode<T>) node.getLeft();

		pivot.setParent(node.getParent());

		if (!node.getParent().isEmpty()) {
			if (node.getParent().getLeft().equals(node)) {
				node.getParent().setLeft(pivot);
			} else {
				node.getParent().setRight(pivot);
			}
		}

		node.setLeft(pivot.getRight());
		node.getLeft().setParent(node);

		node.setParent(pivot);
		pivot.setRight(node);

		return pivot;
	}

}
