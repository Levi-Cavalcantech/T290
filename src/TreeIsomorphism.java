import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeIsomorphism {
    private final Graph graph;
    private String validationMessage;

    public TreeIsomorphism(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph nao pode ser nulo");
        }
        this.graph = graph;
        this.validationMessage = "Aguardando validação";
    }

    public Graph getGraph() {
        return graph;
    }

    // Valida se é árvore: Conexa e com V-1 arestas
    public boolean isTree() {
        int V = graph.V();
        int E = graph.E();

        if (V == 0) {
            validationMessage = "Erro: Grafo vazio.";
            return false;
        }

        if (E != V - 1) {
            validationMessage = "Falha: O grafo possui " + V + " vértices e " + E + " arestas (Deveria ter V-1).";
            return false;
        }

        // Verifica conectividade via DFS
        boolean[] visited = new boolean[V];
        dfs(0, visited);

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                validationMessage = "Falha: O grafo não é conexo (vértice " + i + " isolado).";
                return false;
            }
        }

        validationMessage = "Sucesso: A estrutura é uma árvore válida.";
        return true;
    }

    private void dfs(int v, boolean[] visited) {
        visited[v] = true;
        for (int w : graph.adj(v)) {
            if (!visited[w]) {
                dfs(w, visited);
            }
        }
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    // Algoritmo de remoção iterativa de folhas para achar o centro
    public int[] getCenters() {
        int V = graph.V();
        if (V == 1) return new int[]{0};

        int[] degree = new int[V];
        List<Integer> leaves = new ArrayList<>();

        for (int v = 0; v < V; v++) {
            int d = 0;
            for (int w : graph.adj(v)) d++;
            degree[v] = d;
            if (d == 1) leaves.add(v);
        }

        int processed = leaves.size();
        while (processed < V) {
            List<Integer> newLeaves = new ArrayList<>();
            for (int u : leaves) {
                for (int v : graph.adj(u)) {
                    degree[v]--;
                    if (degree[v] == 1) {
                        newLeaves.add(v);
                    }
                }
            }
            leaves = newLeaves;
            processed += leaves.size();
        }

        int[] result = new int[leaves.size()];
        for (int i = 0; i < leaves.size(); i++) result[i] = leaves.get(i);
        return result;
    }

    // Codificação Canônica
    public String getCanonicalEncoding() {
        int[] centers = getCenters();
        if (centers.length == 1) {
            return encode(centers[0], -1);
        } else {
            // Se houver dois centros, gera os dois códigos e pega o menor lexicograficamente
            String s1 = encode(centers[0], -1);
            String s2 = encode(centers[1], -1);
            return (s1.compareTo(s2) < 0) ? s1 : s2;
        }
    }

    // Função recursiva para gerar o código (parentização balanceada)
    private String encode(int u, int p) {
        List<String> childrenCodes = new ArrayList<>();
        for (int v : graph.adj(u)) {
            if (v != p) {
                childrenCodes.add(encode(v, u));
            }
        }
        
        // Passo essencial: ordenar para garantir que a estrutura seja canônica
        Collections.sort(childrenCodes);

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String code : childrenCodes) {
            sb.append(code);
        }
        sb.append(")");
        return sb.toString();
    }
}