Student System Management
โปรแกรมจัดการข้อมูลนักเรียนเกี่ยวกับการลงทะเบียนเรียน

คำอธิบายโปรแกรม
โปรแกรมช่วยในการจัดการข้อมูลนักเรียน รวมถึงการจัดการรายวิชาและการลงทะเบียนเรียนของนักเรียน โดยมีหน้าตาเป็นรูปแบบ text-based UI

วิธีการเริ่มใช้งานโปรแกรม
สามารถเริ่มใช้งานโปรแกรมได้ที่ studentSystemManagement.jar หรือ src\studentSystemManagement\Main.java

การตั้งค่า Database
ก่อนเริ่มใช้งานโปรแกรม ต้องทำการตั้งค่า Database โดยทำตามขั้นตอนดังต่อไปนี้:

เลือกประเภทการจัดเก็บข้อมูล Database:

หลังเลือกเมนู Storage type แล้วให้ทำการ login โดยกรอกข้อมูล user และ password
เปลี่ยน DBMS (ค่าตั้งต้นเป็น MySQL):

หากต้องการเปลี่ยน DBMS ให้ทำตามขั้นตอนดังนี้:
เลือก Select DBMS ใน Database Configuration
เลือกตัวเลือก Custom
ใส่ DBMS driver โดยรูปแบบจะเป็นดังนี้: com.mysql.cj.jdbc.Driver
ใส่ URL โดยไม่ต้องระบุชื่อ Database รูปแบบจะเป็นดังนี้: jdbc:mysql://localhost:3306/
เชื่อมต่อกับ Database:

เมื่อทำเสร็จแล้วให้เลือกเมนูที่ 3 เพื่อเชื่อมต่อกับ Database