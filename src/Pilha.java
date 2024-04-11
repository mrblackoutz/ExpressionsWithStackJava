// Gustavo Nascimento Siqueira - xxxxxxxx
// Felipe Ujvari Gasparino de Sousa - xxxxxxxx

    /**
     * Initializes a new instance of the {@link Pilha} class with a specified capacity.
     *
     * @param tamanho the initial capacity of the stack.
     * @throws IllegalArgumentException if the specified capacity is negative.
     */
    public class Pilha<T> {
      private T[] elementos; // Array to store the elements of the stack
      private int tamanho; // Capacity of the stack
      private int topo = 0; // Index of the top element in the stack

      @SuppressWarnings("unchecked")
      public Pilha(int tamanho) {
        this.tamanho = tamanho;
        elementos = (T[]) new Object[tamanho]; // Creating a new array of type T with the specified capacity
      }

      /**
       * Adds an item to the top of the stack.
       *
       * @param item the item to be added.
       * @throws IllegalStateException if the stack is full.
       */
      public void push(T item) {
        if (topo == elementos.length) {
          throw new IllegalStateException("A pilha está cheia."); // Throws an exception if the stack is full
        }
        elementos[topo] = item; // Adds the item to the top of the stack
        topo++; // Increments the top index
      }

      /**
       * Removes and returns the item at the top of the stack.
       *
       * @return the item removed from the top of the stack.
       * @throws IllegalStateException if the stack is empty.
       */
      public T pop() {
        if (isEmpty()) {
          throw new IllegalStateException("A pilha está vazia."); // Throws an exception if the stack is empty
        }
        T itemRemovido = elementos[topo - 1]; // Retrieves the item at the top of the stack
        elementos[topo - 1] = null; // Removes the reference to the item from the stack
        topo--; // Decrements the top index
        return itemRemovido; // Returns the removed item
      }

      /**
       * Checks if the stack is empty.
       *
       * @return true if the stack is empty, false otherwise.
       */
      public boolean isEmpty() {
        return topo == 0; // Returns true if the top index is 0, indicating an empty stack
      }

      /**
       * Returns the number of elements in the stack.
       *
       * @return the number of elements in the stack.
       */
      public int size() {
        return tamanho; // Returns the capacity of the stack
      }

      /**
       * Returns the item at the top of the stack without removing it.
       *
       * @return the item at the top of the stack.
       * @throws IllegalStateException if the stack is empty.
       */
      public T peek() {
        if (isEmpty()) {
          throw new IllegalStateException("A pilha está vazia."); // Throws an exception if the stack is empty
        }
        return elementos[topo - 1]; // Returns the item at the top of the stack
      }
    }
