import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("Erro: Informe dois arquivos de entrada.");
            StdOut.println("Ex.: java Main ../dados/arvore1.txt ../dados/arvore2.txt");
            return;
        }

        // 1. Carregamento dos Grafos
        // Usando as classes Graph e In da algs4 que você já tem na pasta
        Graph tree1 = new Graph(new In(args[0]));
        Graph tree2 = new Graph(new In(args[1]));

        // 2. Exibição das Listas de Adjacência (Requisito obrigatório)
        StdOut.println("=== Arvore 1: Lista de Adjacencia ===");
        StdOut.println(tree1);
        StdOut.println();

        StdOut.println("=== Arvore 2: Lista de Adjacencia ===");
        StdOut.println(tree2);
        StdOut.println();

        // 3. Inicialização da Análise
        TreeIsomorphism analysis1 = new TreeIsomorphism(tree1);
        TreeIsomorphism analysis2 = new TreeIsomorphism(tree2);

        // 4. Validação (Interrompe se não for árvore)
        boolean isTree1 = analysis1.isTree();
        boolean isTree2 = analysis2.isTree();

        StdOut.println("Validacao Arvore 1: " + analysis1.getValidationMessage());
        StdOut.println("Validacao Arvore 2: " + analysis2.getValidationMessage());
        StdOut.println();

        if (!isTree1 || !isTree2) {
            StdOut.println("VEREDITO: Operacao abortada. Uma ou ambas as entradas nao sao arvores validas.");
            return;
        }

        // 5. Cálculo e exibição dos Centros
        StdOut.println("Centros da Arvore 1: " + Arrays.toString(analysis1.getCenters()));
        StdOut.println("Centros da Arvore 2: " + Arrays.toString(analysis2.getCenters()));
        StdOut.println();

        // 6. Codificação Canônica
        String code1 = analysis1.getCanonicalEncoding();
        String code2 = analysis2.getCanonicalEncoding();

        StdOut.println("Codificacao Canonica 1: " + code1);
        StdOut.println("Codificacao Canonica 2: " + code2);
        StdOut.println();

        // 7. Veredito Final
        if (code1.equals(code2)) {
            StdOut.println("VEREDITO FINAL: As arvores sao ISOMORFAS.");
        } else {
            StdOut.println("VEREDITO FINAL: As arvores NAO sao isomorfas.");
        }
    }
}