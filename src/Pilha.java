// Gustavo Nascimento Siqueira - xxxxxxxx
// Felipe Ujvari Gasparino de Sousa - xxxxxxxx

    /**
     * Inicializa uma nova instância da classe {@link Pilha} com uma capacidade especificada.
     *
     * @param tamanho a capacidade inicial da pilha.
     * @throws IllegalArgumentException se a capacidade especificada for negativa.
     */
    public class Pilha<T> {
      private T[] elementos; // Array para armazenar os elementos da pilha
      private int tamanho; // Capacidade da pilha
      private int topo = 0; // Índice do elemento do topo da pilha

      @SuppressWarnings("unchecked")
      public Pilha(int tamanho) {
      this.tamanho = tamanho;
      elementos = (T[]) new Object[tamanho]; // Criando um novo array do tipo T com a capacidade especificada
      }

      /**
       * Adiciona um item ao topo da pilha.
       *
       * @param item o item a ser adicionado.
       * @throws IllegalStateException se a pilha estiver cheia.
       */
      public void push(T item) {
      if (topo == elementos.length) {
        throw new IllegalStateException("A pilha está cheia."); // Lança uma exceção se a pilha estiver cheia
      }
      elementos[topo] = item; // Adiciona o item ao topo da pilha
      topo++; // Incrementa o índice do topo
      }

      /**
       * Remove e retorna o item do topo da pilha.
       *
       * @return o item removido do topo da pilha.
       * @throws IllegalStateException se a pilha estiver vazia.
       */
      public T pop() {
      if (isEmpty()) {
        throw new IllegalStateException("A pilha está vazia."); // Lança uma exceção se a pilha estiver vazia
      }
      T itemRemovido = elementos[topo - 1]; // Obtém o item do topo da pilha
      elementos[topo - 1] = null; // Remove a referência ao item da pilha
      topo--; // Decrementa o índice do topo
      return itemRemovido; // Retorna o item removido
      }

      /**
       * Verifica se a pilha está vazia.
       *
       * @return true se a pilha estiver vazia, false caso contrário.
       */
      public boolean isEmpty() {
      return topo == 0; // Retorna true se o índice do topo for 0, indicando uma pilha vazia
      }

      /**
       * Retorna o número de elementos na pilha.
       *
       * @return o número de elementos na pilha.
       */
      public int size() {
      return tamanho; // Retorna a capacidade da pilha
      }

      /**
       * Retorna o item do topo da pilha sem removê-lo.
       *
       * @return o item do topo da pilha.
       * @throws IllegalStateException se a pilha estiver vazia.
       */
      public T peek() {
      if (isEmpty()) {
        throw new IllegalStateException("A pilha está vazia."); // Lança uma exceção se a pilha estiver vazia
      }
      return elementos[topo - 1]; // Retorna o item do topo da pilha
      }
    }
