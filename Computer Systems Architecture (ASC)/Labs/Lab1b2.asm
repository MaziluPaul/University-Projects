;2. z=a+b*b-( 2/(b*b)/(1+(2/(b*b)) )

ASSUME CS:code, DS:data

data SEGMENT

a db 1
b db 2
rez dw ?

data ENDS

code SEGMENT

start:
mov AX, data
mov DS, AX

mov AL, b
mul b
mov CX, AX
;CX = b*b

mov AL, 2
cwd
idiv CX
add AX, 1
;AX = 1 + (2/(b*b))

mov BX, AX
;BX = 1 + (2/(b*b))

mov AL, 2
cwd
idiv CX
;AX = 2/(b*b)
cwd
idiv BX

mov BX, AX
;BX = (2/(b*b))/(1+(2/(b*b)))

mov AL, a
add AX, CX
sub AX, BX
;AX = a+b*b-( 2/(b*b)/(1+(2/(b*b)) )

mov rez, AX
mov AX, 4C00h
int 21h

code ENDS
END start
