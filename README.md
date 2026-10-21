Gestiune Farmacie

Aplicatie desktop in Java pentru evidenta stocurilor si procesarea vanzarilor dintr-o farmacie, conectata la o baza de date MySQL.

Ce face aplicatia:
- Autentificare cu roluri:
* ADMIN: adaugare, editare, stergere medicamente si aprovizionare stoc (receptie marfa de la furnizor).
* FARMACIST: interfata pentru vanzare (adaugare produse in cos, calcul total, selectare sau inregistrare client nou).
- Tranzactii SQL: vanzarea si receptia de marfa folosesc tranzactii JDBC (commit si rollback) pentru consistenta datelor.

Tehnologii:
- Java (Swing)
- MySQL / XAMPP
- JDBC

Conturi de test:
- Admin: admin / admin123
- Farmacist: farmacist / farma123

Cum se ruleaza: (sa nu uit)
1. Porneste Apache si MySQL din XAMPP Control Panel.
2. Deschide phpMyAdmin (http://localhost/phpmyadmin).
3. Creeaza o baza de date noua numita farmacie_db.
4. Importa fisierul farmacie_db.sql din proiect.
5. Deschide proiectul in IntelliJ IDEA.
6. Adauga driverul MySQL Connector (.jar) in Project Structure -> Libraries.
7. Ruleaza clasa MainFrame din pachetul GUI.
