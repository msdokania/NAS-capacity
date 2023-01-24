import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.LinkedList;

class NASMaxFlow
{
    static final int V = 194;

    boolean bfs(int new_Graph[][], int s, int t, int parent[])
    {
        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;


        while (queue.size()!=0)
        {
            int u = queue.poll();

            for (int v=0; v<V; v++)
            {
                if (visited[v]==false && new_Graph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return (visited[t] == true);
    }


    int fordFulkersonMaxFlow(int graph[][], int s, int t)
    {
        int u, v;
        int new_Graph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
            new_Graph[u][v] = graph[u][v];

        int parent[] = new int[V];
        int max_flow = 0;

        while (bfs(new_Graph, s, t, parent))
        {
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, new_Graph[u][v]);
            }
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                new_Graph[u][v] -= path_flow;
                new_Graph[v][u] += path_flow;
            }
            max_flow += path_flow;
        }
        return max_flow;
    }

    public static void main (String[] args) throws java.lang.Exception {
        int directFlow=0;
        int adjMat[][] = new int[194][194];
        for(int i=0;i<194;i++)
        {
            for(int j=0;j<194;j++)
            {
                adjMat[i][j]=0;
            }
        }
        for (Flight flight : getData())
        {
            int r,c;
            int departure_time=flight.getDept();
            int arrival_time=flight.getArr();
            if ((departure_time>=6 && arrival_time<=24) || ((departure_time>=0 && arrival_time<=6))){
                r=getPort(flight.getSource(),departure_time);
                c=getPort(flight.getDestination(),arrival_time);
                if(r==0 && c==193)
                {
                    directFlow+=flight.getCapacity();
                }
                else {
                    adjMat[r][c] += flight.getCapacity();
                }
            } 
        }
        for(int i=1;i<193;i++)
        {
            for(int j=1;j<193;j++)
            {
                if(i<=j)
                {
                    if((i>0 && j>0) && (i<25 && j<25))
                    {
                            adjMat[i][j] = Integer.MAX_VALUE;
                    }
                    else if((i>24 && j>24) && (i<49 && j<49))
                    {
                            adjMat[i][j] = Integer.MAX_VALUE;
                    }
                    else if((i>48 && j>48) && (i<73 && j<73))
                    {
                        adjMat[i][j] = Integer.MAX_VALUE;
                    }
                    else if((i>72 && j>72) && (i<97 && j<97))
                    {
                        adjMat[i][j] = Integer.MAX_VALUE;
                    }
                    else if((i>96 && j>96) && (i<121 && j<121))
                    {
                        adjMat[i][j] = Integer.MAX_VALUE;
                    }
                    else if((i>120 && j>120) && (i<145 && j<145))
                    {
                        adjMat[i][j] = Integer.MAX_VALUE;
                    }
                    else if((i>144 && j>144) && (i<169 && j<169))
                    {
                        adjMat[i][j] = Integer.MAX_VALUE;
                    }
                    else if((i>168 && j>168))
                    {
                        adjMat[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }
        NASMaxFlow m = new NASMaxFlow();
        int result = directFlow + m.fordFulkersonMaxFlow(adjMat, 0, 193);
        System.out.println("The maximum possible flow = " +result);
    }

    public static int getPort(String d,int a)
    {
        switch (d)
        {
            case "LAX":
                return 0;
            case "SFO":
                return 1+a;
            case "PHX":
                return 25+a;
            case "SEA":
                return 49+a;
            case "DEN":
                return 73+a;
            case "ATL":
                return 97+a;
            case "ORD":
                return 121+a;
            case "BOS":
                return 145+a;
            case "IAD":
                return 169+a;
            case "JFK":
                return 193;
        }
        return -1;
    }

    public static List<Flight> getData() throws IOException {
        int i=0;
        String csvFile = "data.csv";
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        List<Flight> flights = new ArrayList<>();
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            String[] data = line.split(cvsSplitBy);
            flights.add(new Flight(i, data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            i++;
        }
        return flights;
    }
}