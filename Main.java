package main;

import java.util.Scanner;
import java.util.Random;

class Main {
    
    // Funcão para Limpar a Tela
    static void limpar (int howMuch) {
        for (int i=0; i<howMuch; i++) {System.out.println ("\n");}
    }
    
    // Retorna um Char Aleatorio dentro da String do Alfabeto
    static char CharAleatorio(String palavra){
        
        Random random = new Random();
        int letra = random.nextInt(palavra.length());
        return palavra.charAt(letra);
        
    }
    
    // Simplesmente exibe a Matriz na Tela com [i] e [j]
    static void ExibirMatriz (int linhas, int colunas, char[][] mtz) {
        
        for (int i=0; i<linhas; i++) {
            
            System.out.println ("");
            
            for (int j=0; j<colunas; j++) {
                
                System.out.print (" " + mtz[i][j] + " ");
            }
        }
    }
    
    // Gera Aleatoriamente a Ordem que a Palavra será exibida
    static int GerarOrdem () {
        
        // Gera um Número entre "1" e "3".
        int ordem = (int)(Math.random() * (5 - 1)) + 1;
        
        /*
         1 = HORIZONTAL
         2 = VERTICAL
         3 = DIAGONAL
         */
        
        // Caso exista algum Problema, o valor será arredondado.
        if (ordem >= 4) {ordem = 3;}
        
        return ordem;
        
    }
    
    // Com base na Ordem, gera uma Posição que encaixe a Palavra no Caça-Palavras
    static int GerarPosicao (int linhas, int colunas, int tamanhoPalavra, int ordem) {
        
        // "posicao" é uma Variável Temporária
        int posicao = 0;
        
        // Gera um Número que servirá de Posição, e retorna apenas se o Número obedecer o Limite
        switch (ordem) {
            case 1: // HORIZONTAL
                while (true)
                {
                    posicao = (int)(Math.random() * (linhas - 1)) + 1;
                    if ((posicao + tamanhoPalavra) <= linhas) {break;}
                }
            case 2: // VERTICAL
                while (true)
                {
                    posicao = (int)(Math.random() * (colunas - 1)) + 1;
                    if ((posicao + tamanhoPalavra) <= colunas) {break;}
                }
            default: // DIAGONAL
                while (true)
                {
                    // Por questão de Segurança, opta por escolher o Menor entre Linhas e Colunas
                    int storage = 0; if (linhas >= colunas) {storage = colunas;} else {storage = linhas;}
                    posicao = (int)(Math.random() * ((storage) - 1)) + 1;
                    if ((posicao + tamanhoPalavra) <= linhas) {break;}
                }
                
        }
        
        return posicao;
        
    }
    
    // Faz uma Verificação Completo de Ambas Ordem e Posição, para saber se é
    // possível encaixar a Palavra específica no Caça-Palavras. Caso contrário,
    // será gerada uma nova Posição.
    static char[][] PreencherMatriz (char[][] matriz, int ps, int ordem, int tamanhoPalavra, String palavra, int linhas, int colunas) {
        
        // Variável de Verificação Simples, se estiver "true" até o final, passou na Verificação
        boolean permitidoPreencher = false;
        
        // Verifica o Caminho Completo da Palavra na Matriz, para saber se ela encaixa
        while (true) {
            
            // VERIFICAÇÃO HORIZONTAL
            if (ordem == 1) {
                for (int i = 0; i<tamanhoPalavra; i++) {
                    if (matriz[ps][ps + i] == '.' || matriz[ps][ps + i] == palavra.charAt(i))
                    { permitidoPreencher = true; } else {permitidoPreencher = false; break; } } }
            
            // VERIFICAÇÃO VERTICAL
            if (ordem == 2) {
                for (int i = 0; i<tamanhoPalavra; i++) {
                    if (matriz[ps + i][ps] == '.' || matriz[ps + i][ps] == palavra.charAt(i))
                    { permitidoPreencher = true; } else {permitidoPreencher = false; break; } } }
            
            // VERIFICAÇÃO DIAGONAL DIREITA-BAIXO
            if (ordem == 3) {
                for (int i = 0; i<tamanhoPalavra; i++) {
                    if (matriz[ps + i][ps + i] == '.' || matriz[ps + i][ps + i] == palavra.charAt(i))
                    { permitidoPreencher = true; } else {permitidoPreencher = false; break; } } }
            
            if (permitidoPreencher == true) {break;} //PERMITIDO APLICAÇÃO
            
            else { ordem = GerarOrdem(); ps = GerarPosicao (linhas, colunas, tamanhoPalavra, ordem); }
            
        }
        
        // APLICAÇÃO HORIZONTAL
        if (ordem == 1) {
            for (int i = 0; i<tamanhoPalavra; i++) {
                matriz[ps][ps + i] = palavra.charAt(i);
            } }
        
        // APLICAÇÃO VERTICAL
        if (ordem == 2) {
            for (int i = 0; i<tamanhoPalavra; i++) {
                matriz[ps + i][ps] = palavra.charAt(i);
            } }
        
        // APLICAÇÃO DIAGONAL DIREITA-BAIXO
        if (ordem == 3) {
            for (int i = 0; i<tamanhoPalavra; i++) {
                matriz[ps + i][ps + i] = palavra.charAt(i);
            } }
        
        // Retorna a mesma matriz, porém com a Palavra Anexada
        return matriz;
        
    }
    
    
    
    // Início do Programa
    public static void main(String[] args) {
        
        Scanner sc = new Scanner (System.in);
        
        // Cria Array que irá conter as Palavras
        String[] palavras;
        
        // Variavel que define tamanho de Array de Palavras
        int arraysize;
        
        // Variável responsável por definir tamanho Minimo da Matriz
        int tamanhoMinimo = 0;
        
        System.out.println ("\n||| TRABALHO 02 |||");
        System.out.println ("\n||| by Rafael Neuwirth");
        
        limpar(5);
        
        System.out.println ("[?] Quantas palavras deseja inserir?");
        arraysize = sc.nextInt();
        
        palavras = new String[arraysize];
        
        // Loop para Solicitar as Palavras ao Jogador
        for (int i=0; i<arraysize; i++) {
            
            limpar(30);
            
            System.out.println ("[ PALAVRA " + (i+1) + " ] Insira alguma Palavra");
            
            limpar(2);
            
            // Por segurança, o Limite de Tamanho da Palavra é de 12 Caracteres
            if (i == 0) {System.out.println ("[!] Limite de Tamanho: 12 Caracteres\n");}
            palavras[i] = sc.next();
            
            // Define a Palavra para Caixa-Alta
            palavras[i] = palavras[i].toUpperCase();
            
            // Caso a Palavra ultrapasse o Limite Seguro, terá que inserir novamente
            // Usa o Tamanho da Palavra mais longa como base para definir o Tamanho Minimo da Matriz
            if (palavras[i].length() > 12) {--i;} else
                if (palavras[i].length() > tamanhoMinimo)
                {tamanhoMinimo = palavras[i].length();}
            
        }
        
        // Define o Tamanho Minimo: ELE MESMO * 2.5 + (quantidade de palavras) dividido por Três.
        tamanhoMinimo = (tamanhoMinimo * (int)2.5 + (int)(arraysize / 3));
        
        int linhas, colunas;
        
        // Loop Infinito que quebra ao Inserir o Tamanho correto de Linhas do Caça-Palavras
        while (true) {
            
            limpar(30);
            
            System.out.println ("[DEFINIR LINHAS] Insira o Tamanho [MINIMO: " + (tamanhoMinimo) + "]\n");
            linhas = sc.nextInt();
            
            if (linhas >= (tamanhoMinimo)) {break;}
            
        }
        
        // Loop Infinito que quebra ao Inserir o Tamanho correto de Colunas do Caça-Palavras
        while (true) {
            
            limpar(30);
            
            System.out.println ("[DEFINIR COLUNAS] Insira o Tamanho [MINIMO: " + (tamanhoMinimo) + "]\n");
            colunas = sc.nextInt();
            
            if (colunas >= (tamanhoMinimo)) {break;}
            
        }
        
        // Cria e Inicializa a Matriz, que será preenchida inicialmente com "Pontos" (.)
        char[][] matriz;
        matriz = new char[linhas][colunas];
        
        // Preenche a Matriz com char de Pontos
        for (int i=0; i<linhas; i++) {
            
            for (int j=0; j<colunas; j++) {
                
                matriz[i][j] = '.';
                
            }
        }
        
        limpar(30);
        
        // Preenche todas as Palavras do Array no Caça-Palavras
        for (int i = 0; i<arraysize; i++) {
            
            // Gera ORDEM e POSIÇÃO para a Palavra Específica
            int ordem = GerarOrdem();
            int posicao = GerarPosicao (linhas, colunas, palavras[i].length(), ordem);
            
            PreencherMatriz (matriz, posicao, ordem, palavras[i].length(), palavras[i], linhas, colunas);
            
        }
        
        // Cria uma "Matriz Secundaria", que é o Caça-Palavras sem preenchimento Aleatorio de Letras
        char[][] matrizRevelada;
        matrizRevelada = new char[linhas][colunas];
        
        // Preenche cada char individualmente da "MatrizRevelada"
        for (int i=0; i<linhas; i++) {
            
            for (int j=0; j<colunas; j++) {
                
                matrizRevelada[i][j] = matriz[i][j];
            }
        }
        
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        // Preenche a Matriz com alguma Letra Aleatoria da String do Alfabeto
        for (int i=0; i<linhas; i++) {
            
            for (int j=0; j<colunas; j++) {
                
                if (matriz[i][j] == '.')
                { matriz[i][j] = CharAleatorio(alfabeto); }
            }
        }
        
        
        limpar(15);
        ExibirMatriz (linhas, colunas, matriz);
        limpar(2);
        
        System.out.print ("> DIGITE ALGO E CLIQUE ENTER PARA REVELAR PALAVRAS ESCONDIDAS");
        
        limpar(1);
        
        palavras[0] = sc.next();
        
        limpar(12);
        ExibirMatriz (linhas, colunas, matrizRevelada);
        limpar(2);
        
    }
}
