package org.ms.iknow.core.type;

public class Synapse extends ElementBase {

    private Neuron   parent;
    private String   parentId;
    private Neuron   child;
    private String   childId;
    private Relation relation;
    private int      weight;

    public Synapse() {
        super();        
    }
  
    public Synapse(Neuron parent, Relation relation, Neuron child) {
        super();
        initPair(parent, relation, child, 0);
    }

    public Synapse(Neuron parent, Relation relation, Neuron child, int weight) {
        super();
        initPair(parent, relation, child, weight);
    }

    private void initPair(Neuron parent, Relation relation, Neuron child, int weight) {
        //parent.getSynapseIds().add(this.id);
      parent.addSynapse(this);  
      setParent(parent);
        setChild(child);
        this.relation = relation;
        this.weight = weight;
    }

    public Neuron getParent() {
        return parent;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParent(Neuron parent) {
        this.parent = parent;
        if (parent != null) {
            this.parentId = parent.getId();
        }
    }

    public Relation getRelation() {
        return relation;
    }

    public Neuron getChild() {
        return child;
    }

    public String getChildId() {
        return childId;
    }

    public void setChild(Neuron child) {
        this.child = child;
        if (child != null) {
            this.childId = child.getId();
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        setChangeDate();
    }

    public Synapse clone() {
        Synapse s = new Synapse();
        s.id = this.id;
        s.parentId = this.parentId;
        s.childId = this.childId;
        s.relation = this.relation;
        s.weight = this.weight;
        s = (Synapse)cloneElementBase(s);
        return s;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n  " + this.getClass().getName() + "\n");
        builder.append(super.toString() + "\n");
        builder.append("  parentId   = " + parentId + "\n");
        builder.append("  childId    = " + childId + "\n");
        if (relation != null) {
            builder.append("  relation   = " + relation.getValue() + "\n");
        } else {
            builder.append("  relation   = null\n");
        }
        builder.append("  weight     = " + weight + "\n");

        return builder.toString();
    }
}
