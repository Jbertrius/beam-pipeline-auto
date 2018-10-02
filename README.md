# Pipeline-Apache-Beam

Ce programme est dédié à l'extraction, la transformation et le nettoyage des données issues de différentes bases de données.
L'architecture des transformations est illustré ci-dessous:

<img src="https://github.com/Jbertrius/beam-pipeline-auto/blob/master/Pipeline.png"
     alt="Pipeline Image"
      />  
      
Le programme est basé sur **Apache Beam**, modèle de programmation unifiée open source pour définir et exécuter des flux de données, y compris ETL, traitement par lot et en flux.
Dans le dossier notebook, on retrouve les scripts de machine learning utilisé pour exploiter les données générées par notre programme. 

Le but finale est d'automatiser l'extraction et l'execution du script grâce à **Apache Airflow** de façon périodique.
      
**To execute the program**  
`mvn compile exec:java -Dexec.mainClass=etai.Vinextract -Pdirect-runner`
