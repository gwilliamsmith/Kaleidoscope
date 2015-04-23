package graphvisualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Graph {

    public ArrayList<GraphNode> nodes = new ArrayList<>();

    public void add(GraphNode gn) {
        nodes.add(gn);
    }//end add

    public void biconnect(GraphNode n1, GraphNode n2) {
        if (nodes.contains(n1) && nodes.contains(n2)) {
            if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
                n1.connect(n2);
                n2.connect(n1);
            }//end if
        }//end if
        else {
            if (!nodes.contains(n1)) {
                System.out.println("The first node is not in the graph!");
            }//end else
            else if (!nodes.contains(n2)) {
                System.out.println("The second node is not in the graph!");
            }//end else if
            else if (!nodes.contains(n1) && !nodes.contains(n2)) {
                System.out.println("Neither node is in the graph!");
            }//end else if
        }//end else
    }//end biconnect

    public void biconnect(GraphNode n1, GraphNode n2, boolean edge) {
        if (nodes.contains(n1) && nodes.contains(n2)) {
            if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
                n1.connect(n2, edge);
                n2.connect(n1, edge);
            }//end if
        }//end if
        else {
            if (!nodes.contains(n1)) {
                System.out.println("The first node is not in the graph!");
            }//end else
            else if (!nodes.contains(n2)) {
                System.out.println("The second node is not in the graph!");
            }//end else if
            else if (!nodes.contains(n1) && !nodes.contains(n2)) {
                System.out.println("Neither node is in the graph!");
            }//end else if
        }//end else
    }//end biconnect

    public void biconnect(GraphNode n1, GraphNode n2, Color color) {
        if (nodes.contains(n1) && nodes.contains(n2)) {
            if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
                n1.connect(n2, color);
                n2.connect(n1, color);
            }//end if
        }//end if
        else {
            if (!nodes.contains(n1)) {
                System.out.println("The first node is not in the graph!");
            }//end else
            else if (!nodes.contains(n2)) {
                System.out.println("The second node is not in the graph!");
            }//end else if
            else if (!nodes.contains(n1) && !nodes.contains(n2)) {
                System.out.println("Neither node is in the graph!");
            }//end else if
        }//end else
    }//end biconnect

    public void biconnect(GraphNode n1, GraphNode n2, Color color, int newHealth) {
        if (nodes.contains(n1) && nodes.contains(n2)) {
            if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
                n1.connect(n2, color, newHealth);
                n2.connect(n1, color, newHealth);
            }//end if
        }//end if
        else {
            if (!nodes.contains(n1)) {
                System.out.println("The first node is not in the graph!");
            }//end else
            else if (!nodes.contains(n2)) {
                System.out.println("The second node is not in the graph!");
            }//end else if
            else if (!nodes.contains(n1) && !nodes.contains(n2)) {
                System.out.println("Neither node is in the graph!");
            }//end else if
        }//end else
    }//end biconnect

    public void biconnect(GraphNode n1, GraphNode n2, Color color, int startHealth, int mutationPercentage) {
        if (nodes.contains(n1) && nodes.contains(n2)) {
            if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
                n1.connect(n2, color, startHealth, mutationPercentage);
                n2.connect(n1, color, startHealth, mutationPercentage);
            }//end if
        }//end if
        else {
            if (!nodes.contains(n1)) {
                System.out.println("The first node is not in the graph!");
            }//end else
            else if (!nodes.contains(n2)) {
                System.out.println("The second node is not in the graph!");
            }//end else if
            else if (!nodes.contains(n1) && !nodes.contains(n2)) {
                System.out.println("Neither node is in the graph!");
            }//end else if
        }//end else
    }//end biconnect

    public void biconnect(GraphNode n1, GraphNode n2, GraphTupleInfo gti) {
        if (nodes.contains(n1) && nodes.contains(n2)) {
            if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
                n1.connect(n2, gti);
                n2.connect(n1, gti);
            }//end if
        }//end if
        else {
            if (!nodes.contains(n1)) {
                System.out.println("The first node is not in the graph!");
            }//end else
            else if (!nodes.contains(n2)) {
                System.out.println("The second node is not in the graph!");
            }//end else if
            else if (!nodes.contains(n1) && !nodes.contains(n2)) {
                System.out.println("Neither node is in the graph!");
            }//end else if
        }//end else
    }//end biconnect

    public Graph graphTrace(GraphNode start, GraphNode target, GraphNode in) {
        ArrayList<NodeTuple> out = new ArrayList<>();
        ArrayList<Boolean> searcher = new ArrayList<>();
        target.searchPath = true;
        for (GraphTuple connection : start.connections) {
            GraphNode gn = connection.location;
            if (gn == target) {
                start.searchPath = true;
                connection.searched = true;
            } //end if
            else {
                if (gn != in && !connection.searched) {
                    connection.searched = true;
                    searcher.add(graphTracePass(gn, target, start));
                } //end if
            } //end else
        } //end for
        if (searcher.contains(true)) {
            start.searchPath = true;
        }//end if
        for (GraphNode gn2 : nodes) {
            if (gn2.searchPath) {
                for (GraphTuple gt : gn2.connections) {
                    if (gt.location.searchPath) {
                        out.add(new NodeTuple(gn2, gt.location));
                    }//end if
                }//end for
            }//end if
        }//end for
        refreshAll();
        return trimSearch(out);
    }//end graphSearch

    private boolean graphTracePass(GraphNode start, GraphNode target, GraphNode in) {
        ArrayList<Boolean> boolList = new ArrayList<>();
        GraphNode gn;
        boolean out = false;
        for (GraphTuple connection : start.connections) {
            gn = connection.location;
            if (gn == target) {
                connection.searched = true;
                gn.searchPath = true;
                start.searchPath = true;
                out = true;
            } //end if
            else {
                if (gn != in && !connection.searched) {
                    connection.searched = true;
                    boolList.add(graphTracePass(gn, target, start));
                } //end if
            } //end else
        } //end for
        if (boolList.contains(true)) {
            start.searchPath = true;
            out = true;
        }//end if
        return out;
    }//end graphSearchPass

    private Graph trimSearch(ArrayList<NodeTuple> in) {
        ArrayList<NodeTuple> out = new ArrayList<>();
        NodeTuple temp3;
        boolean add;
        for (NodeTuple in1 : in) {
            add = true;
            temp3 = in1;
            if (out.isEmpty()) {
                out.add(temp3);
            }//end if
            else {
                for (NodeTuple out1 : out) {
                    if (temp3.compare(temp3, out1) == 0) {
                        add = false;
                    } //end if
                } //end for
                if (add) {
                    out.add(temp3);
                }//end if
            }//end else
        } //end for
        return buildSearch(out);
    }//end trimSearch

    private Graph buildSearch(ArrayList<NodeTuple> in) {
        Graph temp = new Graph();
        for (NodeTuple in1 : in) {
            GraphNode n1 = new GraphNode(in1.node1);
            GraphNode n2 = new GraphNode(in1.node2);
            boolean n1in = false;
            boolean n2in = false;
            for (GraphNode gn : temp.nodes) {
                if (gn.id == n1.id) {
                    n1in = true;
                }//end if
                if (gn.id == n2.id) {
                    n2in = true;
                }//end if
            }//end for
            if (!n1in) {
                temp.add(n1);
            }//end if
            if (!n2in) {
                temp.add(n2);
            }//end if
        } //end for
        for (NodeTuple nt : in) {
            GraphNode n1 = null;
            GraphNode n2 = null;
            for (GraphNode gn : temp.nodes) {
                if (gn.id == nt.node1.id) {
                    n1 = gn;
                }//end if
                else if (gn.id == nt.node2.id) {
                    n2 = gn;
                }//end else if
            }//end for
            if (n1 != null && n2 != null) {
                if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
                    biconnect(n1, n2);
                }//end if
            }//end for
        }//end for
        return temp;
    }//end buildSearch

    public TreeNode<GraphNode> bfs(GraphNode start, GraphNode end) {
        refreshAll();
        if (start != null && end != null) {
            MyQueue<TreeNode> list = new MyQueue<>();
            list.enqueue(new TreeNode<>(start));
            TreeNode<GraphNode> current;
            while (list.size() > 0) {
                TreeNode<GraphNode> temp = list.dequeue();
                temp.data.searched = true;
                current = temp;
                if (current.data == end) {
                    return current;
                }//end if
                for (GraphTuple gt : current.data.connections) {
                    if (!gt.location.searched) {
                        temp = new TreeNode<>(gt.location);
                        temp.parent = current;
                        list.enqueue(temp);
                    }//end if
                }//end for
            }//end while
        }//end if
        return null;
    }//end bfs

    public void refreshAll() {
        for (GraphNode gn : nodes) {
            gn.refresh();
        }//end for
    }//end refreshAll

    public void paint(Graphics g) {
        for (GraphNode r : this.nodes) {
            g.setColor(r.getColor());
            if (r.food <= 0) {
                g.setColor(Color.WHITE);
            }//end if
            g.fillRect(r.x, r.y, r.height, r.width);
            for (GraphTuple gt : r.connections) {
                g.setColor(gt.getColor());
                if (r.isConnected(gt.location) && gt.location.isConnected(r)) {
                    g.drawLine(r.x + r.width / 2, r.y + r.height / 2, gt.location.x + gt.location.width / 2, gt.location.y + gt.location.height / 2);
                }//end if
            }//end for
        }//end for

    }
}//end Graph
