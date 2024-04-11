// Gustavo Nascimento Siqueira - xxxxxxxx
// Felipe Ujvari Gasparino de Sousa - xxxxxxxx
import java.util.Scanner;

public class App {
    /**
     * Método para verificar se uma expressão é válida em termos de parênteses, colchetes e chaves.
     * Utiliza uma pilha para verificar se cada parêntese/colchete/chave aberto tem um correspondente fechado.
     * @param expression a expressão a ser verificada
     * @return true se a expressão for válida, false caso contrário
     */
    public static boolean isValidExpression(String expression) {
        Pilha<Character> stack = new Pilha<Character>(10000);
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((top == '(' && ch != ')') || (top == '{' && ch != '}') || (top == '[' && ch != ']')) {
                    return false;
                }
            }
        }
        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para verificar se uma expressão contém apenas variáveis válidas.
     * Uma variável válida é considerada como uma letra seguida opcionalmente por operadores e outras letras.
     * @param expression a expressão a ser verificada
     * @return true se a expressão contiver apenas variáveis válidas, false caso contrário
     */
    public static boolean isValidVariable(String expression) {
        expression = expression.replaceAll("[(){}\\[\\]\\s]", "");
        return expression.matches("^(?!.*\\d)[A-Za-z](?: *[-+*/^] *([A-Za-z]))*$");
    }

    /**
     * Método para determinar a precedência de um operador.
     * Quanto maior o valor retornado, maior a precedência.
     * @param operator o operador a ser verificado
     * @return o valor de precedência do operador
     */
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    /**
     * Método para converter uma expressão infixa para notação polonesa reversa (RPN).
     * Utiliza uma pilha para armazenar os operadores e manipula a saída de acordo com as regras de precedência.
     * @param expression a expressão infixa a ser convertida
     * @return a expressão em notação polonesa reversa
     */
    public static String convertToReversePoloneseNotation(String expression) {
        String output = "";
        Pilha<Character> stack = new Pilha<Character>(10000);

        for (char c : expression.toCharArray()) {
            // Se for um identificador (letra ou número), concatena na saída
            if (Character.isLetterOrDigit(c)) {
                output += c;
            }
            // Se for parêntese de abertura, empilha
            else if (c == '(') {
                stack.push(c);
            }
            // Se for parêntese de fechamento
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output += stack.pop();
                }
                // Remover o parêntese aberto da pilha
                stack.pop();
            }
            // Se for um operador
            else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    output += stack.pop();
                }
                stack.push(c);
            }
        }

        // Esvaziar a pilha e mover tudo para a saída
        while (!stack.isEmpty()) {
            output += stack.pop();
        }
        return output;
    }

    /**
     * Método auxiliar para verificar se um dado char já existe no array de chars.
     * @param array o array de chars a ser verificado
     * @param key o char a ser procurado
     * @param count o número de elementos válidos no array
     * @return true se o char existir no array, false caso contrário
     */
    private static boolean contains(char[] array, char key, int count) {
        for (int i = 0; i < count; i++) {
            if (array[i] == key) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para substituir as variáveis na expressão RPN pelos seus valores numéricos.
     * @param rpnExpression a expressão RPN com as variáveis
     * @param variables o array de variáveis
     * @param values o array de valores correspondentes às variáveis
     * @return a expressão RPN com as variáveis substituídas pelos valores
     */
    public static String replaceVariablesInRPN(String rpnExpression, char[] variables, double[] values) {
        String resultExpression = "";
        for (int i = 0; i < rpnExpression.length(); i++) {
            char c = rpnExpression.charAt(i);
            if (Character.isLetter(c)) {
                // Encontrar o valor correspondente para a variável e substituí-lo
                for (int j = 0; j < variables.length; j++) {
                    if (variables[j] == c) {
                        resultExpression += Double.toString(values[j]) + " ";;
                        break;
                    }
                }
            } else {
                resultExpression += c + " ";
            }
        }
        return resultExpression;
    }

    /**
     * Método para avaliar uma expressão RPN.
     * Utiliza uma pilha para armazenar os operandos e realiza as operações de acordo com os operadores encontrados.
     * @param rpnWithValues a expressão RPN com os valores numéricos
     * @return o resultado da avaliação da expressão
     * @throws IllegalArgumentException se a expressão RPN for inválida
     * @throws UnsupportedOperationException se ocorrer uma divisão por zero
     */
    public static double evaluateRPN(String rpnWithValues) {
        Pilha<Double> stack = new Pilha<Double>(10000); // Capacidade supondo uma expressão grande
        String[] tokens = rpnWithValues.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) {
              continue;
            }

            // Se for um número, empilhe na pilha.
            if (token.matches("-?\\d+(\\.\\d+)?")) { // Regex para detectar inteiros e números decimais, incluindo negativos
                stack.push(Double.parseDouble(token));
            } else {
                // Deve ser um operador, então pop dois elementos para aplicar a operação
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                double result;

                // Aplica o operador
                switch (token) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand == 0) throw new UnsupportedOperationException("Não podemos dividir por zero.");
                        result = firstOperand / secondOperand;
                        break;
                    case "^":
                        result = Math.pow(firstOperand, secondOperand);
                        break;
                    default:
                        throw new IllegalArgumentException("Operador inválido encontrado: " + token);
                }

                // Empilhar o resultado da operação
                stack.push(result);
            }
        }

        // O resultado final deve ser o último item na pilha
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("A expressão RPN é inválida.");
        }

        return stack.pop();
    }

    /**
     * Método principal que implementa o menu de opções para o usuário.
     * @param args os argumentos de linha de comando (não utilizados)
     * @throws Exception se ocorrer algum erro durante a execução do programa
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String expression = "";
        boolean isExpressionValid = false;
        String rpnExpression = "";
        char[] variables = new char[10000];
        double[] values = new double[variables.length];
        int choice = 0;

        do {
            System.out.println("Menu:");
            System.out.println("1. Entrar com a expressão aritmética (notação infixa).");
            System.out.println("2. Entrar com os valores numéricos das variáveis.");
            System.out.println("3. Converter a expressão para notação posfixa e exibir.");
            System.out.println("4. Avaliar a expressão.");
            System.out.println("5. Encerrar o programa.");
            System.out.print("Escolha uma opção: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, por favor digite um número entre 1 e 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Digite a expressão na notação infixa: ");
                    expression = scanner.nextLine();
                    isExpressionValid = isValidExpression(expression) && isValidVariable(expression);
                    if (isExpressionValid) {
                        System.out.println("Expressão armazenada com sucesso.");
                    } else {
                        System.out.println("Expressão inválida. Por favor, tente novamente.");
                    }
                    break;
                case 2:
                    if (!isExpressionValid) {
                        System.out.println("Por favor, insira e valide uma expressão na opção 1 antes de prosseguir.");
                    } else {
                        int variablesCount = 0;

                        // Identifica as variáveis (considera-se que variáveis são caracteres alfabéticos únicos)
                        for (int i = 0; i < expression.length(); i++) {
                            char ch = expression.charAt(i);
                            // Verifica se o caractere é uma letra e ainda não foi adicionado ao array
                            if (Character.isLetter(ch) && !contains(variables, ch, variablesCount)) {
                                variables[variablesCount++] = ch;
                            }
                        }

                        // Solicita ao usuário os valores para cada variável identificada
                        for (int i = 0; i < variablesCount; i++) {
                            while (true) {
                                try {
                                    System.out.print("Digite o valor para a variável " + variables[i] + ": ");
                                    double value = Double.parseDouble(scanner.nextLine());
                                    values[i] = value;
                                    break; // Sai do loop depois de uma entrada bem-sucedida
                                } catch (NumberFormatException e) {
                                    System.out.println("Valor inválido. Por favor, insira um número válido.");
                                }
                            }
                        }

                        System.out.println("Todos os valores numéricos das variáveis foram armazenados.");
                    }
                    break;
                case 3:
                    if (!isExpressionValid) {
                        System.out.println("Por favor, insira e valide uma expressão na opção 1 antes de prosseguir.");
                    } else {
                        rpnExpression = convertToReversePoloneseNotation(expression);
                        System.out.println("Notação Polonesa Reversa: " + rpnExpression);
                    }
                    break;
                case 4:
                    if (rpnExpression.isEmpty()) {
                        System.out.println("Por favor, realize a conversão da expressão na opção 3 antes de prosseguir.");
                    } else {
                        // Esta será a expressão com as variáveis substituídas
                        String substitutedExpression = replaceVariablesInRPN(rpnExpression, variables, values);
                        System.out.println("Expressão com variáveis substituídas: " + substitutedExpression);

                        try {
                            double result = evaluateRPN(substitutedExpression);
                            System.out.println("O resultado da expressão é: " + result);
                        } catch (Exception e) {
                            System.out.println("Houve um erro ao calcular a expressão: " + e.getMessage());
                        }
                    }
                    break;
                case 5:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha um número entre 1 e 5.");
            }
        } while (choice != 5);

        scanner.close();
    }
}

// References:
// https://www.devmedia.com.br/usando-generics-em-java/28981
// https://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression/

// https://github.com/mrblackoutz/ExpressionsWithStackJava