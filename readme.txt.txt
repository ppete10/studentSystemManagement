Student System Management
โปรแกรมจัดการข้อมูลนักเรียนเกี่ยวกับการลงทะเบียนเรียน

คำอธิบายโปรแกรม
โปรแกรมช่วยในการจัดการข้อมูลนักเรียน รวมถึงการจัดการรายวิชาและการลงทะเบียนเรียนของนักเรียนได้ โดยมีหน้าตาเป็นรูปแบบ text-base ui

วิธีการเริ่มใช้งาน
ก่อนเริ่มใช้งานโปรแกรม ต้องทำการตั้งค่า Database โดยทำตามขั้นตอนดังต่อไปนี้:

แก้ไขที่คลาส DatabaseConnection ในแพ็กเกจ repository.jdbc
เปลี่ยน Database Driver โดยแก้ค่าในฟิลด์ DBMS (ค่าเริ่มต้นเป็น MySQL)
แก้ไข URL, USERNAME, PASSWORD ตาม Host ของคุณ (จำเป็นต้องทำ)
เมื่อได้ทำการตั้งค่า Database เรียบร้อยแล้ว สามารถเริ่มใช้งานโปรแกรมได้ที่ studentSystemManagement.jar หรือ src\studentSystemManagement\Main.java