package exesis.view.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name = "relatorioBean")
public class AdminRelatorioBean extends AbstractBean{
    
    private BarChartModel barraTurmaPorDisciplinas;
    private BarChartModel barraDisciplinaPorTurmas;
    private PieChartModel pizzaDesempenhoConteudo;
    private BarChartModel barraComparacaoTurmaAluno;
 
    @PostConstruct
    public void init() {
        createBarModels();
    }
 
    private BarChartModel iniciarBarraTurmaPorDisciplinas() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries disciplina = new ChartSeries();
        disciplina.setLabel("Disciplinas");
        disciplina.set("Matemática", 8.7);
        disciplina.set("Português", 6);
        disciplina.set("Ciências", 8.7);
        disciplina.set("História", 6.9);
        disciplina.set("Geografia", 8.0);
        disciplina.set("Filosofia", 2.7);
        disciplina.set("Sociologia", 8.6);
        disciplina.set("Artes", 6.7);
        disciplina.set("Inglês", 7.8);
        disciplina.set("Educação Física", 5.7);
 
        model.addSeries(disciplina);
        return model;
    }
    
    private BarChartModel iniciarBarraDisciplinaPorTurmas() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries disciplina = new ChartSeries();
        disciplina.setLabel("Turmas");
        disciplina.set("Turma 5ºA", 8.7);
        disciplina.set("Turma 5ºB", 4.5);
        disciplina.set("Turma 5ºC", 7.3);
        disciplina.set("Turma 6ºA", 5.5);
        disciplina.set("Turma 6ºB", 9.4);
        disciplina.set("Turma 6ºC", 6.3);
        disciplina.set("Turma 7ºA", 5.1);
        disciplina.set("Turma 7ºB", 8.1);
        disciplina.set("Turma 7ºC", 7.4);
        disciplina.set("Turma 8ºA", 6.6);
        disciplina.set("Turma 8ºB", 8.6);
        disciplina.set("Turma 8ºC", 7.2);
        
        model.addSeries(disciplina);
        return model;
    }
    
    private void criarPizzaDesempenhoConteudo(){
        pizzaDesempenhoConteudo = new PieChartModel();
        pizzaDesempenhoConteudo.set("Ótimo", 2);
        pizzaDesempenhoConteudo.set("Bom", 9);
        pizzaDesempenhoConteudo.set("Regular", 12);
        pizzaDesempenhoConteudo.set("Ruim", 16);
        pizzaDesempenhoConteudo.set("Péssimo", 4);
        
        pizzaDesempenhoConteudo.setTitle("Equação de 2º grau");
        pizzaDesempenhoConteudo.setFill(true);
        pizzaDesempenhoConteudo.setShowDataLabels(true);
        pizzaDesempenhoConteudo.setDiameter(220);
        pizzaDesempenhoConteudo.setMouseoverHighlight(true);
        pizzaDesempenhoConteudo.setLegendPosition("w");
        pizzaDesempenhoConteudo.setShadow(false);
        
    }
    
    private BarChartModel iniciarBarraComparacaoTurmaAluno() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries aluno = new ChartSeries();
        aluno.setLabel("Aluno");
        aluno.set("Equação do 1º Grau", 8.0);
        aluno.set("Equação do 2º Grau", 6.7);
        aluno.set("Polígonos", 7.8);
        aluno.set("Probabilidade", 4.6);
        aluno.set("financeira", 5.8);
        aluno.set("Geomatria plana", 7.8);
        
        ChartSeries turma = new ChartSeries();
        turma.setLabel("Turma");
        turma.set("Equação do 1º Grau", 6);
        turma.set("Equação do 2º Grau", 9.0);
        turma.set("Polígonos", 6.7);
        turma.set("Probabilidade",  3);
        turma.set("financeira", 7.8);
        turma.set("Geomatria plana", 7.8);
        
        model.addSeries(aluno);
        model.addSeries(turma);
         
        return model;
    }
     
    private void createBarModels() {
        criarBarraTurmaPorDisciplinas();
        criarBarraDisciplinaPorTurmas();
        criarPizzaDesempenhoConteudo();
        criarBarraTurmaAluno();
    }
     
        private void criarBarraTurmaAluno(){
        barraComparacaoTurmaAluno = iniciarBarraComparacaoTurmaAluno(); 
        barraComparacaoTurmaAluno.setTitle("Aluno - Zézinho");
        barraComparacaoTurmaAluno.setAnimate(true);
        barraComparacaoTurmaAluno.setLegendPosition("ne");
        barraComparacaoTurmaAluno.setAnimate(true);
        barraComparacaoTurmaAluno.setLegendPosition("ne");
        Axis yAxis = barraComparacaoTurmaAluno.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
    private void criarBarraTurmaPorDisciplinas() {
        barraTurmaPorDisciplinas = iniciarBarraTurmaPorDisciplinas();
        barraTurmaPorDisciplinas.setTitle("Média da Turma em cada disciplina");
        Axis xAxis = barraTurmaPorDisciplinas.getAxis(AxisType.X);
        xAxis.setLabel("Disciplinas");
         
        Axis yAxis = barraTurmaPorDisciplinas.getAxis(AxisType.Y);
        yAxis.setLabel("Notas");
        
        yAxis.setMin(0);
        yAxis.setMax(10.0);
        yAxis.setTickInterval("1");
    }
     
    private void criarBarraDisciplinaPorTurmas() {
        barraDisciplinaPorTurmas = iniciarBarraDisciplinaPorTurmas();
         
        barraDisciplinaPorTurmas.setTitle("Média de cada turma em uma disciplina");
        
        barraDisciplinaPorTurmas.setSeriesColors("aabbee");
        Axis xAxis = barraDisciplinaPorTurmas.getAxis(AxisType.X);
        xAxis.setLabel("Turmas");
         
        Axis yAxis = barraDisciplinaPorTurmas.getAxis(AxisType.Y);
        yAxis.setLabel("Notas");
        yAxis.setMin(0);
        yAxis.setMax(10.0);
        yAxis.setTickInterval("1");
    }

    public BarChartModel getBarraTurmaPorDisciplinas() {
        return barraTurmaPorDisciplinas;
    }

    public void setBarraTurmaPorDisciplinas(BarChartModel barraTurmaPorDisciplinas) {
        this.barraTurmaPorDisciplinas = barraTurmaPorDisciplinas;
    }

    public BarChartModel getBarraDisciplinaPorTurmas() {
        return barraDisciplinaPorTurmas;
    }

    public void setBarraDisciplinaPorTurmas(BarChartModel barraDisciplinaPorTurmas) {
        this.barraDisciplinaPorTurmas = barraDisciplinaPorTurmas;
    }

    public PieChartModel getPizzaDesempenhoConteudo() {
        return pizzaDesempenhoConteudo;
    }

    public void setPizzaDesempenhoConteudo(PieChartModel pizzaDesempenhoConteudo) {
        this.pizzaDesempenhoConteudo = pizzaDesempenhoConteudo;
    }

    public BarChartModel getBarraComparacaoTurmaAluno() {
        return barraComparacaoTurmaAluno;
    }

    public void setBarraComparacaoTurmaAluno(BarChartModel barraComparacaoTurmaAluno) {
        this.barraComparacaoTurmaAluno = barraComparacaoTurmaAluno;
    }
    
    
}
