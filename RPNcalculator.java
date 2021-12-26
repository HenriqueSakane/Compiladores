import java.math.BigDecimal;
import java.util.*;

public class RPNcalculator {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Escreva o numero de caracteres da sua expressao RPN:");
		int numero = in.nextInt();
		System.out.printf("Agora escreva os %d caracteres separando eles com (Enter):", numero);
		// variavel que vai ser concatenada
		String concatenado = "";
		for (int i = 0; i < numero; i++) {
			String aux = in.next();
			if (concatenado.equals("")) {
				concatenado = concatenado + aux;
			} else {
				concatenado = (concatenado + " ") + aux;
			}
		}

		boolean parar = false;
		BigDecimal func = func(concatenado, parar);
	}

	// iniciar a pilha
	static Stack<String> calculadora = new Stack<String>();

	public static BigDecimal func(String equacao, boolean parar) {
		// iniciando a tabela de id's
		String[] id = new String[4];
		id[0] = "x";
		id[1] = "y";
		id[2] = "z";
		id[3] = "w";
		// parte numerica referente a cada id
		String[] numeric = new String[4];
		numeric[0] = "5";
		numeric[1] = "7";
		numeric[2] = "13";
		numeric[3] = "19";
		// separar os tokens por espaços
		String tokens[] = equacao.split("\\ ");
		String aux[] = equacao.split("\\ ");
		// for para buscar os tokens no vetor
		for (int i = 0; i < tokens.length; i++) {
			String palavra = tokens[i];
			char character = palavra.charAt(0);
			int ascii = (int) character;
			// caso o token for numero
			if (ascii >= 48 && ascii <= 57) {
				aux[i] = "Num";
				// caso for soma
			} else if (ascii == 43) {
				aux[i] = "PLUS";
				// caso for diminuição
			} else if (ascii == 45) {
				aux[i] = "MINUS";
				// caso for multiplicacao
			} else if (ascii == 42) {
				aux[i] = "STAR";
				// caso for divisao
			} else if (ascii == 47) {
				aux[i] = "SLASH";
				// caso for exponenciacao
			} else if (ascii == 94) {
				aux[i] = "EOF";
				//caso contrario ID
			} else {
				aux[i] = "ID";
			}

		}
		for (String token : tokens) {
			if ("+-*/^".contains(token)) {
				// criando o operador
				BigDecimal n1 = new BigDecimal(calculadora.pop());
				BigDecimal n2 = new BigDecimal(calculadora.pop());
				// check o token
				switch (token) {
				case "+":
					calculadora.push(n1.add(n2).toString());
					break;
				case "-":
					calculadora.push(n1.subtract(n2).toString());
					break;
				case "*":
					calculadora.push(n1.multiply(n2).toString());
					break;
				case "/":
					calculadora.push(n1.divide(n2).toString());
					break;
				case "^":
					calculadora.push(n1.pow(n2.intValue()).toString());
					break;
				}
			} else {
				// se nao for operador ele tenta armazenar o numero
				if (ehNumber(token)) {
					calculadora.push(token);
					// se nao for numero tem que transmitir o erro
				} else {
					// check se o valor foi previamente passado
					if (token.equals("x") || token.equals("y") || token.equals("z") || token.equals("w")) {
						for (int i = 0; i < numeric.length; i++) {
							if (token.equals(id[i])) {
								calculadora.push(numeric[i]);
							}
						}
					} else {
						System.out.printf("Error: Unexpected character: %s\n", token);
						parar = true;
						break;
					}
				}
			}

		}
		if (parar == true) {
			return new BigDecimal(0);
		} else {
			String resultado = calculadora.pop();
			if (ehNumber(resultado)) {
				for (int i = 0; i < tokens.length; i++) {
					System.out.printf("Token [type=%s, lexeme=%s]\n", aux[i], tokens[i]);
				}
				System.out.println("\nSaida: " + resultado);
				return new BigDecimal(0);
			} else {
				System.out.println("Erro na escrita");
				return new BigDecimal(0);
			}

		}
	}

	public static boolean ehNumber(String match) {
		return match.matches("[0-9.]*");
	}

}
