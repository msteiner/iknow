package org.ms.iknow.core.type;

public class Synapse extends ElementBase {

    private Neuron   parent;
    private String   parentId;
    private Neuron   child;
    private String   childId;
    @Deprecated
    private Relation relation;
    private Relation relationParentChild;
    private Relation relationChildParent;
    private int      weight;

    @Deprecated
    public Synapse(Neuron parent, Relation relation, Neuron child) {
        super();
        initPair(parent, relation, child);
    }

    @Deprecated
    public Synapse(Neuron parent, Relation relation, Neuron child, int weight) {
        super();
        initPair(parent, relation, child);
        this.weight = weight;
    }

    public Synapse(Neuron parent, Relation relationParentChild, Relation relationChildParent, Neuron child) {
        super();
        initPair(parent, relationParentChild, relationChildParent, child);
    }

    public Synapse(Neuron parent, Relation relationParentChild, Relation relationChildParent, Neuron child, int weight) {
        super();
        initPair(parent, relationParentChild, relationChildParent, child);
        this.weight = weight;
    }

    @Deprecated
    private void initPair(Neuron parent, Relation relation, Neuron child) {
        setParent(parent);
        setChild(child);
        this.relation = relation;
    }

    private void initPair(Neuron parent, Relation relationParentChild, Relation relationChildParent, Neuron child) {
        setParent(parent);
        parent.addSynapse(this);
        setChild(child);
        child.addSynapse(this);
        this.relationParentChild = relationParentChild;
        this.relationChildParent = relationChildParent;
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

    @Deprecated
    public Relation getRelation() {
        return relation;
    }

    public Relation getRelationParentChild() {
        return relationParentChild;
    }

    public Relation getRelationChildParent() {
        return relationChildParent;
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

    public Synapse cloneSimple() {
        Synapse s = new Synapse(this.parent, this.relationParentChild, this.relationChildParent, this.child, this.weight);
        s.id = this.id;
        s.setParent(null);
        s.setChild(null);
        return s;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n  " + this.getClass().getName() + "\n");
        builder.append(super.toString() + "\n");
        builder.append("  parentId = " + parentId + "\n");
        builder.append("  childId = " + childId + "\n");
        if (relationParentChild != null) {
            builder.append("  relationParentChild = " + relationParentChild.getDescription() + "\n");
        } else {
            builder.append("  relationParentChild = null\n");
        }
        if (relationChildParent != null) {
            builder.append("  relationChildParent = " + relationChildParent.getDescription() + "\n");
        } else {
            builder.append("  relationChildParent = null\n");
        }
        builder.append("  weight = " + weight + "\n");

        return builder.toString();
    }
}
