package exesis.model;


public class Tag extends EntidadeDominio{
    public Tag(){}
    public Tag(String nome){
        this.nome = nome;
    }
    public Tag(int id, String nome){
        this.id = id;
        this.nome = nome;
    }
    
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
//    @Override
//    public boolean equals(Object o){
//        if (this == o)
//			return true;
//        if (o == null || getClass() != o.getClass())
//		return false;
//        Tag tag = (Tag) o;
//        if (getNome() != null ? !getNome().equals(tag.getNome()): tag.getNome()!= null)
//            return false;
//	return true;
//    }
}
