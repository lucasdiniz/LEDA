package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T>
		implements SplayTree<T> {

	private final BSTNode<T> NIL = new BSTNode<T>();

	private void splay(BSTNode<T> node) {
		if(node.getParent().equals(NIL)){
			return;
		}

		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		BSTNode<T> grandParent = (BSTNode<T>) parent.getParent();


		if(!parent.equals(NIL) && !grandParent.equals(NIL)){

			if(parent.getData().compareTo(node.getData()) < 0 && grandParent.getData().compareTo(parent.getData()) < 0){
				leftRotation(parent);
			}

            else if(parent.getData().compareTo(node.getData()) > 0 && grandParent.getData().compareTo(parent.getData()) > 0){
                rightRotation(parent);
            }

            else if(parent.getData().compareTo(node.getData()) > 0 && grandParent.getData().compareTo(parent.getData()) < 0){
                rightRotation(parent);
            }

            else if(parent.getData().compareTo(node.getData()) < 0 && grandParent.getData().compareTo(parent.getData()) > 0){
                leftRotation(parent);
            }

		}

        else if(!parent.equals(NIL) && grandParent.equals(NIL)){
            if(parent.getData().compareTo(node.getData()) < 0){
                leftRotation(parent);
            }

            else if(parent.getData().compareTo(node.getData()) > 0){
                //System.out.println("aqui mzr " + node.getData());
                rightRotation(parent);
                //System.out.println("aqui mzr " + node.getRight().getData());
            }
        }

	}


	@Override
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

		splay((BSTNode<T>)node.getParent());
	}


	@Override
	protected void insert(BSTNode<T> node, T element, BSTNode<T> parent) {

		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
			splay(node);
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
        splay(node);
	}

    @Override
    public BSTNode<T> search(BSTNode<T> node, T element) {

        if (node.isEmpty()) {
            return emptyNode;
        }

        T nodeData = node.getData();

        if (nodeData.equals(element)) {
            splay(node);
            return node;

        } else if (nodeData.compareTo(element) > 0) { //element eh menor, procuraremos esquerda

            return search((BSTNode<T>) node.getLeft(), element);

        } else { //element eh maior, procuraremos a direita

            return search((BSTNode<T>) node.getRight(), element);

        }
        //splay(node);

    }



	public void leftRotation(BSTNode<T> node){
		BSTNode<T> newRoot = Util.leftRotation(node);

		if(node == root){
			root = newRoot;
		}

	}

	public void rightRotation(BSTNode<T> node){
		BSTNode<T> newRoot = Util.rightRotation(node);

		if(node == root){
			root = newRoot;
		}
	}



}
